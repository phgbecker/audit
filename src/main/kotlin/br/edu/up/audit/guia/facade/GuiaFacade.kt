package br.edu.up.audit.guia.facade

import br.edu.up.audit.autenticacao.facade.AutenticacaoFacade
import br.edu.up.audit.guia.api.dto.GuiaDTO
import br.edu.up.audit.guia.business.GuiaBusiness
import br.edu.up.audit.guia.business.objects.GuiaResultadoPesquisa.Encontrada
import br.edu.up.audit.guia.business.objects.GuiaResultadoPesquisa.NaoEncontrada
import br.edu.up.audit.guia.business.objects.GuiaRetornoProcessamento.NaoProcessado
import br.edu.up.audit.guia.business.objects.GuiaRetornoProcessamento.Processado
import br.edu.up.audit.guia.converter.toDTO
import br.edu.up.audit.guia.converter.toDTOsResumido
import br.edu.up.audit.guia.converter.toInstancias
import br.edu.up.audit.guia.converter.toModel
import br.edu.up.audit.guia.exception.GuiaCadastradaException
import br.edu.up.audit.guia.exception.GuiaNaoEncontradaException
import br.edu.up.audit.guia.exception.GuiaNaoProcessadaException
import br.edu.up.audit.guia.model.GuiaModel
import br.edu.up.audit.guia.model.StatusProcessamento
import br.edu.up.audit.guia.model.StatusProcessamento.PROCESSADO
import br.edu.up.audit.util.toDate
import org.springframework.stereotype.Service
import java.util.*

@Service
class GuiaFacade(private val guiaBusiness: GuiaBusiness,
                 private val autenticacaoFacade: AutenticacaoFacade) {

    fun cadastrar(cnpj: String, entrada: GuiaDTO): GuiaDTO =
            guiaBusiness.obter(
                    cnpj = cnpj,
                    codigo = entrada.codigo
            ).run {
                when (this) {
                    is Encontrada ->
                        throw GuiaCadastradaException("A guia com o código ${entrada.codigo} já está cadastrada!")

                    is NaoEncontrada ->
                        guiaBusiness.processar(
                                cnpj = cnpj,
                                instancias = entrada.toInstancias()
                        ).run {
                            when (this) {
                                is Processado ->
                                    guiaBusiness.cadastrar(
                                            guia = GuiaModel(
                                                    codigo = entrada.codigo,
                                                    codigoBeneficiario = entrada.codigoBeneficiario,
                                                    idadeBeneficiario = entrada.idadeBeneficiario,
                                                    cid = entrada.cid,
                                                    dataEmissao = entrada.dataEmissao.toDate()!!,
                                                    dataCriacao = Date(),
                                                    statusProcessamento = PROCESSADO,
                                                    indicativoFraude = resultado.indicativo,
                                                    scoreIndicativo = resultado.score,
                                                    empresa = autenticacaoFacade.obterEmpresaSessao(),
                                                    auditor = autenticacaoFacade.obterAuditorSessao()
                                            ).apply {
                                                procedimentos = entrada.procedimentos!!.toModel(empresa, auditor, guia = this)
                                            }
                                    ).toDTO()

                                is NaoProcessado ->
                                    throw GuiaNaoProcessadaException(mensagem)
                            }
                        }
                }
            }

    fun listar(cnpj: String, dataInicial: Date? = null, dataFinal: Date? = null, status: StatusProcessamento? = null): List<GuiaDTO> =
            guiaBusiness.listar(cnpj, dataInicial, dataFinal, status).toDTOsResumido()

    fun listarProcessadas(cnpj: String, dataInicial: Date? = null, dataFinal: Date? = null): List<GuiaDTO> =
            guiaBusiness.listar(
                    cnpj = cnpj,
                    dataInicial = dataInicial,
                    dataFinal = dataFinal,
                    status = PROCESSADO
            ).toDTOsResumido()

    fun obter(cnpj: String, codigo: Long): GuiaDTO =
            guiaBusiness.obter(cnpj, codigo)
                    .run {
                        when (this) {
                            is Encontrada ->
                                guia.toDTO()

                            is NaoEncontrada ->
                                throw GuiaNaoEncontradaException(mensagem)
                        }
                    }

    fun remover(cnpj: String, codigo: Long) =
            guiaBusiness.obter(cnpj, codigo)
                    .run {
                        when (this) {
                            is Encontrada ->
                                guiaBusiness.remover(guia)

                            is NaoEncontrada ->
                                throw GuiaNaoEncontradaException(mensagem)
                        }
                    }
}