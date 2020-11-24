package br.edu.up.audit.guia.business.objects

import com.opencsv.bean.CsvBindByName

class GuiaInstanciaClassificada {
    @CsvBindByName(column = "label")
    val indicativo: Int? = null

    @CsvBindByName(column = "score")
    val score: Float? = null
}