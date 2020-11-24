package br.edu.up.audit.motor.business

import br.edu.up.audit.autenticacao.facade.AutenticacaoFacade
import br.edu.up.audit.motor.business.objects.MotorParametrosTreinamento
import br.edu.up.audit.motor.business.objects.MotorRetornoPesquisa
import br.edu.up.audit.motor.business.objects.MotorRetornoPesquisa.Encontrado
import br.edu.up.audit.motor.business.objects.MotorRetornoPesquisa.NaoEncontrado
import br.edu.up.audit.motor.model.MotorModel
import br.edu.up.audit.motor.repository.MotorRepository
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Service
class MotorBusiness(private val repositorio: MotorRepository,
                    private val parametros: MotorParametrosTreinamento,
                    private val autenticacaoFacade: AutenticacaoFacade) {

    private val executorTarefas: ExecutorService = Executors.newSingleThreadExecutor()

    fun cadastrar(motor: MotorModel): MotorModel =
            repositorio.save(motor)

    fun listar(cnpj: String): List<MotorModel> =
            repositorio.findAllByEmpresaCnpjOrderByDataCriacaoDesc(cnpj)

    fun obter(cnpj: String): MotorRetornoPesquisa =
            repositorio.findFirstByEmpresaCnpjOrderByDataCriacaoDesc(cnpj)
                    ?.run {
                        Encontrado(motor = this)
                    }
                    ?: NaoEncontrado(mensagem = "O motor de inferência não está cadastrado")

    fun agendarTreinamento(cnpj: String, arquivo: ByteArray) {
        executorTarefas.submit(
                MotorTarefasTreinamento(cnpj, arquivo, parametros)
        ).also {
            executorTarefas.submit(
                    MotorTarefaCadastrarRetorno(
                            retorno = it,
                            motorBusiness = this@MotorBusiness,
                            empresa = autenticacaoFacade.obterEmpresaSessao(),
                            auditor = autenticacaoFacade.obterAuditorSessao()
                    )
            )
        }
    }
}