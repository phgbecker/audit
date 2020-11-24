package br.edu.up.audit.relatorio.api.dto

data class GraficoDTO(
        val legendas: List<String>,
        val dataset: DatasetDTO
)