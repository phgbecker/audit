package br.edu.up.audit.relatorio.api.dto

data class DatasetDTO(
        val sim: List<Int>,
        val nao: List<Int>,
        val inconclusivo: List<Int>
)