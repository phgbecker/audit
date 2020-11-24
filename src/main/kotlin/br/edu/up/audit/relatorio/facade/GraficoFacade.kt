package br.edu.up.audit.relatorio.facade

import br.edu.up.audit.relatorio.api.dto.DatasetDTO
import br.edu.up.audit.relatorio.api.dto.GraficoDTO
import br.edu.up.audit.relatorio.business.GraficoBusiness
import br.edu.up.audit.relatorio.converter.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class GraficoFacade(private val graficoBusiness: GraficoBusiness) {

    fun guiasPorIdade(cnpj: String, dataInicial: Date? = null, dataFinal: Date? = null): GraficoDTO =
            graficoBusiness.guiasPorIdade(
                    cnpj = cnpj,
                    dataInicial = dataInicial,
                    dataFinal = dataFinal
            ).run {
                GraficoDTO(
                        legendas = this.indicesDistintos(),
                        dataset = DatasetDTO(
                                sim = this.totaisComIndicativo(),
                                nao = this.totaisSemIndicativo(),
                                inconclusivo = this.totaisInconclusivos()
                        )
                )
            }

    fun guiasPorEspecialidade(cnpj: String, dataInicial: Date? = null, dataFinal: Date? = null): GraficoDTO =
            graficoBusiness.guiasPorEspecialidade(
                    cnpj = cnpj,
                    dataInicial = dataInicial,
                    dataFinal = dataFinal
            ).run {
                GraficoDTO(
                        legendas = this.indicesDistintos(),
                        dataset = DatasetDTO(
                                sim = this.totaisComIndicativo(),
                                nao = this.totaisSemIndicativo(),
                                inconclusivo = this.totaisInconclusivos()
                        )
                )
            }

    fun guiasPorPrestador(cnpj: String, dataInicial: Date? = null, dataFinal: Date? = null): GraficoDTO =
            graficoBusiness.guiasPorPrestador(
                    cnpj = cnpj,
                    dataInicial = dataInicial,
                    dataFinal = dataFinal
            ).run {
                GraficoDTO(
                        legendas = this.indicesDistintos(),
                        dataset = DatasetDTO(
                                sim = this.totaisComIndicativo(),
                                nao = this.totaisSemIndicativo(),
                                inconclusivo = this.totaisInconclusivos()
                        )
                )
            }

    fun guiasPorTempo(cnpj: String, dataInicial: Date? = null, dataFinal: Date? = null): GraficoDTO =
            graficoBusiness.guiasPorTempo(
                    cnpj = cnpj,
                    dataInicial = dataInicial,
                    dataFinal = dataFinal
            ).run {
                GraficoDTO(
                        legendas = this.indicesDistintos(),
                        dataset = DatasetDTO(
                                sim = this.totaisComIndicativo(),
                                nao = this.totaisSemIndicativo(),
                                inconclusivo = this.totaisInconclusivos()
                        )
                )
            }
}