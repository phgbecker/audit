package br.edu.up.audit.relatorio.facade

import br.edu.up.audit.guia.facade.GuiaFacade
import br.edu.up.audit.relatorio.api.dto.RelatorioDTO
import br.edu.up.audit.relatorio.api.dto.VisaoGeralDTO
import br.edu.up.audit.relatorio.business.RelatorioBusiness
import br.edu.up.audit.util.toDate
import org.springframework.stereotype.Service

@Service
class RelatorioFacade(private val guiaFacade: GuiaFacade,
                      private val graficoFacade: GraficoFacade,
                      private val relatorioBusiness: RelatorioBusiness) {

    fun geral(cnpj: String, dataInicial: String?, dataFinal: String?): VisaoGeralDTO =
            VisaoGeralDTO(
                    indicador = relatorioBusiness.geral(cnpj),
                    relatorio = RelatorioDTO(
                            grafico = graficoFacade.guiasPorTempo(
                                    cnpj = cnpj,
                                    dataInicial = dataInicial?.toDate(),
                                    dataFinal = dataFinal?.toDate()
                            )
                    )
            )

    fun guiasPorIdade(cnpj: String, dataInicial: String?, dataFinal: String?): RelatorioDTO =
            RelatorioDTO(
                    grafico = graficoFacade.guiasPorIdade(
                            cnpj = cnpj,
                            dataInicial = dataInicial?.toDate(),
                            dataFinal = dataFinal?.toDate()
                    ),
                    detalhamento = guiaFacade.listarProcessadas(
                            cnpj = cnpj,
                            dataInicial = dataInicial?.toDate(),
                            dataFinal = dataFinal?.toDate()
                    )
            )

    fun guiasPorEspecialidade(cnpj: String, dataInicial: String?, dataFinal: String?): RelatorioDTO =
            RelatorioDTO(
                    grafico = graficoFacade.guiasPorEspecialidade(
                            cnpj = cnpj,
                            dataInicial = dataInicial?.toDate(),
                            dataFinal = dataFinal?.toDate()
                    ),
                    detalhamento = guiaFacade.listarProcessadas(
                            cnpj = cnpj,
                            dataInicial = dataInicial?.toDate(),
                            dataFinal = dataFinal?.toDate()
                    )
            )

    fun guiasPorPrestador(cnpj: String, dataInicial: String?, dataFinal: String?): RelatorioDTO =
            RelatorioDTO(
                    grafico = graficoFacade.guiasPorPrestador(
                            cnpj = cnpj,
                            dataInicial = dataInicial?.toDate(),
                            dataFinal = dataFinal?.toDate()
                    ),
                    detalhamento = guiaFacade.listarProcessadas(
                            cnpj = cnpj,
                            dataInicial = dataInicial?.toDate(),
                            dataFinal = dataFinal?.toDate()
                    )
            )

    fun guiasPorTempo(cnpj: String, dataInicial: String?, dataFinal: String?): RelatorioDTO =
            RelatorioDTO(
                    grafico = graficoFacade.guiasPorTempo(
                            cnpj = cnpj,
                            dataInicial = dataInicial?.toDate(),
                            dataFinal = dataFinal?.toDate()
                    ),
                    detalhamento = guiaFacade.listarProcessadas(
                            cnpj = cnpj,
                            dataInicial = dataInicial?.toDate(),
                            dataFinal = dataFinal?.toDate()
                    )
            )
}