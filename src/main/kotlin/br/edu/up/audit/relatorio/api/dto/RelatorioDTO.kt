package br.edu.up.audit.relatorio.api.dto

import br.edu.up.audit.guia.api.dto.GuiaDTO

data class RelatorioDTO(
        val grafico: GraficoDTO,
        val detalhamento: List<GuiaDTO>? = null
)