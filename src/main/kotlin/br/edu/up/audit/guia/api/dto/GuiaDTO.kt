package br.edu.up.audit.guia.api.dto

import br.edu.up.audit.guia.model.IndicativoFraude
import br.edu.up.audit.guia.model.StatusProcessamento

data class GuiaDTO(
        val codigo: Long,
        val codigoBeneficiario: Long,
        val idadeBeneficiario: Int,
        val cid: String,
        val auditor: String? = null,
        val dataEmissao: String,
        val dataCriacao: String? = null,
        val statusProcessamento: StatusProcessamento? = null,
        val indicativoFraude: IndicativoFraude? = null,
        val scoreIndicativo: Float? = null,
        val procedimentos: List<ProcedimentoDTO>? = null
)