package br.edu.up.audit.auditor.business

import br.edu.up.audit.auditor.business.objects.AuditorRetornoPesquisa
import br.edu.up.audit.auditor.business.objects.AuditorRetornoPesquisa.Encontrado
import br.edu.up.audit.auditor.business.objects.AuditorRetornoPesquisa.NaoEncontrado
import br.edu.up.audit.auditor.model.AuditorModel
import br.edu.up.audit.auditor.repository.AuditorRepository
import org.springframework.stereotype.Service

@Service
class AuditorBusiness(private val repositorio: AuditorRepository) {

    fun cadastrar(auditor: AuditorModel): AuditorModel =
            repositorio.save(auditor)

    fun editar(auditor: AuditorModel): AuditorModel =
            repositorio.save(auditor)

    fun listar(cnpj: String): List<AuditorModel> =
            repositorio.findAllByEmpresaCnpjAndIsAtivoIsTrueOrderByNomeAsc(cnpj)

    fun obter(cnpj: String, cpf: String): AuditorRetornoPesquisa =
            repositorio.findByEmpresaCnpjAndCpfAndIsAtivoIsTrue(cnpj, cpf)
                    ?.run {
                        Encontrado(auditor = this)
                    }
                    ?: NaoEncontrado(mensagem = "Não foi possível obter o auditor a partir do CPF $cpf")

    fun obter(cnpj: String, cpf: String, email: String): AuditorRetornoPesquisa =
            repositorio.findByEmpresaCnpjAndCpfOrEmailAndIsAtivoIsTrue(cnpj, cpf, email)
                    ?.run {
                        Encontrado(auditor = this)
                    }
                    ?: NaoEncontrado(mensagem = "Não foi possível obter o auditor a partir do CPF $cpf")

    fun remover(auditor: AuditorModel) {
        repositorio.save(
                auditor.copy(isAtivo = false)
        )
    }
}