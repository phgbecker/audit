package br.edu.up.audit.relatorio.api.dto

data class IndicadorDTO(
        val beneficiarios: Int,
        val guias: Int,
        val procedimentos: Int,
        val guiasSemIndicativo: Int,
        val guiasComIndicativo: Int,
        val guiasInconclusivas: Int
)