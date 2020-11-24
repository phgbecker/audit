package br.edu.up.audit.guia.business

import br.edu.up.audit.guia.business.objects.GuiaInstancia
import br.edu.up.audit.guia.business.objects.GuiaResultadoClassificacao
import br.edu.up.audit.guia.business.objects.GuiaResultadoPesquisa
import br.edu.up.audit.guia.business.objects.GuiaResultadoPesquisa.Encontrada
import br.edu.up.audit.guia.business.objects.GuiaResultadoPesquisa.NaoEncontrada
import br.edu.up.audit.guia.business.objects.GuiaRetornoProcessamento
import br.edu.up.audit.guia.business.objects.GuiaRetornoProcessamento.NaoProcessado
import br.edu.up.audit.guia.business.objects.GuiaRetornoProcessamento.Processado
import br.edu.up.audit.guia.converter.toCSV
import br.edu.up.audit.guia.converter.toInstanciaClassificada
import br.edu.up.audit.guia.model.GuiaModel
import br.edu.up.audit.guia.model.IndicativoFraude.*
import br.edu.up.audit.guia.model.StatusProcessamento
import br.edu.up.audit.guia.repository.GuiaRepository
import br.edu.up.audit.motor.facade.MotorFacade
import org.springframework.stereotype.Service
import java.util.*

@Service
class GuiaBusiness(private val repositorio: GuiaRepository,
                   private val motorFacade: MotorFacade) {

    fun cadastrar(guia: GuiaModel): GuiaModel =
            repositorio.save(guia)

    fun listar(cnpj: String, dataInicial: Date? = null, dataFinal: Date? = null, status: StatusProcessamento? = null): List<GuiaModel> =
            repositorio.findAllByEmpresaCnpjAndSituacaoOrderByDataCriacaoDesc(
                    cnpj = cnpj,
                    dataInicial = dataInicial,
                    dataFinal = dataFinal,
                    status = status
            )

    fun obter(cnpj: String, codigo: Long): GuiaResultadoPesquisa =
            repositorio.findByEmpresaCnpjAndCodigo(cnpj, codigo)
                    ?.run {
                        Encontrada(guia = this)
                    }
                    ?: NaoEncontrada(mensagem = "Não foi possível obter a guia a partir do código $codigo")

    fun processar(cnpj: String, instancias: List<GuiaInstancia>): GuiaRetornoProcessamento =
            try {
                motorFacade.inferir(
                        cnpj = cnpj,
                        arquivo = instancias.toCSV()
                ).toInstanciaClassificada()
                        .run {
                            when {
                                any { it.score!! <= INCONCLUSIVO.score!! } ->
                                    Processado(
                                            resultado = GuiaResultadoClassificacao(
                                                    indicativo = INCONCLUSIVO,
                                                    score = minBy { it.score!! <= INCONCLUSIVO.score!! }?.score!!
                                            )
                                    )

                                any { it.indicativo == SIM.valor } ->
                                    Processado(
                                            resultado = GuiaResultadoClassificacao(
                                                    indicativo = SIM,
                                                    score = maxBy { it.indicativo == SIM.valor }?.score!!
                                            )
                                    )

                                else ->
                                    Processado(
                                            resultado = GuiaResultadoClassificacao(
                                                    indicativo = NAO,
                                                    score = maxBy { it.indicativo == NAO.valor }?.score!!
                                            )
                                    )
                            }
                        }
            } catch (e: Exception) {
                NaoProcessado("Não foi possível processar a guia. Detalhes: ${e.localizedMessage}")
            }

    fun remover(guia: GuiaModel) =
            repositorio.delete(guia)
}