package br.edu.up.audit.guia.api.dto

import java.math.BigDecimal

data class ProcedimentoDTO(
        val codigo: Long,
        val descricao: String,
        val especialidade: String,
        val especialidadePrestador: String,
        val quantidade: Int,
        val valor: BigDecimal
)