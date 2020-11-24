package br.edu.up.audit.guia.business.objects

import com.opencsv.bean.CsvBindByName
import java.math.BigDecimal

data class GuiaInstancia(
        @CsvBindByName(column = "idade")
        val idadeBeneficiario: Int,

        @CsvBindByName(column = "codigo_evento")
        val cid: String,

        @CsvBindByName(column = "grau_principal")
        val descricao: String,

        @CsvBindByName(column = "especialidade")
        val especialidade: String,

        @CsvBindByName(column = "especialidade_recebedor")
        val especialidadePrestador: String,

        @CsvBindByName(column = "qtd_pago")
        val quantidade: Int,

        @CsvBindByName(column = "valor_pago")
        val valor: BigDecimal
)