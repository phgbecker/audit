package br.edu.up.audit.guia.converter

import br.edu.up.audit.guia.business.objects.GuiaInstancia
import com.opencsv.CSVWriter.DEFAULT_SEPARATOR
import com.opencsv.bean.StatefulBeanToCsvBuilder
import java.io.FileWriter
import java.nio.file.Files

fun List<GuiaInstancia>.toCSV(): ByteArray =
        with(Files.createTempFile("GuiaInstancia", ".csv").toFile()) {
            FileWriter(this)
                    .use {
                        StatefulBeanToCsvBuilder<GuiaInstancia>(it)
                                .withSeparator(DEFAULT_SEPARATOR)
                                .build()
                                .write(this@toCSV)
                    }

            this.readBytes()
        }