package br.edu.up.audit.guia.business.objects

import br.edu.up.audit.guia.model.IndicativoFraude

data class GuiaResultadoClassificacao(
        val indicativo: IndicativoFraude,
        val score: Float
)