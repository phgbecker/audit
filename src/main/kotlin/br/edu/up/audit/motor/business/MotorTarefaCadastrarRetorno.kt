package br.edu.up.audit.motor.business

import br.edu.up.audit.auditor.model.AuditorModel
import br.edu.up.audit.empresa.model.EmpresaModel
import br.edu.up.audit.motor.business.objects.MotorRetornoTreinamento
import br.edu.up.audit.motor.business.objects.MotorRetornoTreinamento.Falha
import br.edu.up.audit.motor.business.objects.MotorRetornoTreinamento.Sucesso
import br.edu.up.audit.motor.converter.*
import br.edu.up.audit.motor.exception.MotorFalhaExecucaoException
import br.edu.up.audit.motor.model.MotorModel
import java.util.*
import java.util.concurrent.Future

class MotorTarefaCadastrarRetorno(private val retorno: Future<MotorRetornoTreinamento>,
                                  private val motorBusiness: MotorBusiness,
                                  private val empresa: EmpresaModel,
                                  private val auditor: AuditorModel) : Runnable {

    override fun run() {
        while (!retorno.isDone);

        with(retorno.get()) {
            when (this) {
                is Sucesso ->
                    motorBusiness.cadastrar(
                            motor = MotorModel(
                                    registros = metricas.registros(),
                                    registrosTreino = metricas.registrosTreino(),
                                    registrosTeste = metricas.registrosTeste(),
                                    acuracia = metricas.acuracia(),
                                    sensibilidade = metricas.sensibilidade(),
                                    especificidade = metricas.especificidade(),
                                    baseHistorica = nome,
                                    endpoint = endpoint,
                                    dataCriacao = Date(),
                                    empresa = empresa,
                                    auditorModel = auditor
                            )
                    )

                is Falha ->
                    throw MotorFalhaExecucaoException(mensagem)
            }
        }
    }
}