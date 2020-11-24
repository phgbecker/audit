package br.edu.up.audit.guia.converter

import br.edu.up.audit.guia.business.objects.GuiaInstanciaClassificada
import com.opencsv.bean.CsvToBeanBuilder
import java.io.StringReader

fun ByteArray.toInstanciaClassificada(): List<GuiaInstanciaClassificada> =
        StringReader(String(this))
                .use {
                    CsvToBeanBuilder<GuiaInstanciaClassificada>(it)
                            .withType(GuiaInstanciaClassificada::class.java)
                            .build()
                            .parse()
                }