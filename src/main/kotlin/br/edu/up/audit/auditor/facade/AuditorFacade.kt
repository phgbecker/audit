package br.edu.up.audit.auditor.facade

import br.edu.up.audit.auditor.api.dto.AuditorDTO
import br.edu.up.audit.auditor.business.AuditorBusiness
import br.edu.up.audit.auditor.business.objects.AuditorRetornoPesquisa.Encontrado
import br.edu.up.audit.auditor.business.objects.AuditorRetornoPesquisa.NaoEncontrado
import br.edu.up.audit.auditor.converter.toDTO
import br.edu.up.audit.auditor.converter.toDTOs
import br.edu.up.audit.auditor.exception.AuditorCadastradoException
import br.edu.up.audit.auditor.exception.AuditorNaoEncontradoException
import br.edu.up.audit.auditor.model.AuditorModel
import br.edu.up.audit.autenticacao.facade.AutenticacaoFacade
import br.edu.up.audit.util.toBCrypt
import br.edu.up.audit.util.toNumero
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuditorFacade(private val auditorBusiness: AuditorBusiness,
                    private val autenticacaoFacade: AutenticacaoFacade) {

    fun cadastrar(cnpj: String, entrada: AuditorDTO): AuditorDTO =
            auditorBusiness.obter(
                    cnpj = cnpj,
                    cpf = entrada.cpf.toNumero(),
                    email = entrada.email
            ).run {
                when (this) {
                    is Encontrado ->
                        throw AuditorCadastradoException("O auditor com o CPF, e/ou e-mail, informado já está cadastrado!")

                    is NaoEncontrado ->
                        auditorBusiness.cadastrar(
                                auditor = AuditorModel(
                                        cpf = entrada.cpf.toNumero(),
                                        nome = entrada.nome,
                                        email = entrada.email,
                                        telefone = entrada.telefone,
                                        senha = entrada.senha!!.toBCrypt(),
                                        isAdministrador = entrada.administrador ?: false,
                                        isAtivo = true,
                                        dataCriacao = Date(),
                                        dataEdicao = Date(),
                                        empresa = autenticacaoFacade.obterEmpresaSessao())
                        ).toDTO()
                }
            }

    fun editar(cnpj: String, entrada: AuditorDTO): AuditorDTO =
            auditorBusiness.obter(
                    cnpj = cnpj,
                    cpf = entrada.cpf.toNumero()
            ).run {
                when (this) {
                    is Encontrado ->
                        auditorBusiness.editar(
                                auditor = auditor.copy(
                                        nome = entrada.nome,
                                        telefone = entrada.telefone,
                                        senha = entrada.senha?.toBCrypt() ?: auditor.senha,
                                        isAdministrador = entrada.administrador ?: auditor.isAdministrador,
                                        dataEdicao = Date())
                        ).toDTO()

                    is NaoEncontrado ->
                        throw AuditorNaoEncontradoException(mensagem)
                }
            }

    fun listar(cnpj: String): List<AuditorDTO> =
            auditorBusiness.listar(cnpj).toDTOs()

    fun obter(cnpj: String, cpf: String): AuditorDTO =
            auditorBusiness.obter(cnpj, cpf)
                    .run {
                        when (this) {
                            is Encontrado ->
                                auditor.toDTO()

                            is NaoEncontrado ->
                                throw AuditorNaoEncontradoException(mensagem)
                        }
                    }

    fun remover(cnpj: String, cpf: String) =
            auditorBusiness.obter(cnpj, cpf)
                    .run {
                        when (this) {
                            is Encontrado ->
                                auditorBusiness.remover(auditor)

                            is NaoEncontrado ->
                                throw AuditorNaoEncontradoException(mensagem)
                        }
                    }
}