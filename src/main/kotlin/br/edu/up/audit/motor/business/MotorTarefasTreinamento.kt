package br.edu.up.audit.motor.business

import br.edu.up.audit.motor.business.objects.MotorParametrosTreinamento
import br.edu.up.audit.motor.business.objects.MotorRetornoTreinamento
import br.edu.up.audit.motor.business.objects.MotorRetornoTreinamento.Falha
import br.edu.up.audit.motor.business.objects.MotorRetornoTreinamento.Sucesso
import br.edu.up.audit.util.toAnoMesDiaHora
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class MotorTarefasTreinamento(private val cnpj: String,
                              private val arquivo: ByteArray,
                              private val parametros: MotorParametrosTreinamento) : Callable<MotorRetornoTreinamento> {

    private val nome = "${cnpj}-${Date().toAnoMesDiaHora()}"
    private val executorTarefa = Executors.newSingleThreadExecutor()

    override fun call(): MotorRetornoTreinamento =
            try {
                executorTarefa.submit(
                        MotorTarefaUploadBaseHistorica(
                                nome = nome,
                                arquivo = arquivo,
                                repositorio = parametros.repositorio,
                                baseHistorica = parametros.baseHistorica
                        )
                ).apply { while (!isDone); }

                executorTarefa.submit(
                        MotorTarefaTreinamentoModelo(
                                nome = nome,
                                url = "s3://${parametros.repositorio}/${parametros.baseTreinamento}",
                                imagem = parametros.sageMakerImagem,
                                regraExecucao = parametros.sageMakerRegra
                        )
                ).apply { while (!isDone); }

                executorTarefa.submit(
                        MotorTarefaCriacaoModelo(
                                nome = nome,
                                url = "s3://${parametros.repositorio}/${parametros.baseTreinamento}/${nome}",
                                imagem = parametros.sageMakerImagem,
                                regraExecucao = parametros.sageMakerRegra
                        )
                ).apply { while (!isDone); }


                executorTarefa.submit(
                        MotorTarefaCriacaoEndpoint(nome)
                ).apply { while (!isDone); }

                val metricasModelo = executorTarefa.submit(
                        MotorTarefaObtencaoMetricasModelo(
                                nome = nome,
                                namespace = parametros.sageMakerNamespace
                        )
                ).apply { while (!isDone); }

                Sucesso(nome = nome,
                        endpoint = nome,
                        metricas = metricasModelo.get()
                )
            } catch (e: Exception) {
                Falha("Não foi possível treinar o motor para o CNPJ: ${cnpj}. Detalhes: ${e.localizedMessage}")
            }
}