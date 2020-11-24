package br.edu.up.audit.relatorio.converter

import br.edu.up.audit.guia.model.IndicativoFraude
import br.edu.up.audit.relatorio.business.objects.RegistroGrafico

fun List<RegistroGrafico>.indices(): List<String> =
        map {
            it.indice
        }

fun List<RegistroGrafico>.indicesDistintos(): List<String> =
        indices().distinct()

fun List<RegistroGrafico>.totaisComIndicativo(): List<Int> =
        filter {
            it.indicativo == IndicativoFraude.SIM.name
        }.map {
            it.total
        }

fun List<RegistroGrafico>.totaisSemIndicativo(): List<Int> =
        filter {
            it.indicativo == IndicativoFraude.NAO.name
        }.map {
            it.total
        }

fun List<RegistroGrafico>.totaisInconclusivos(): List<Int> =
        filter {
            it.indicativo == IndicativoFraude.INCONCLUSIVO.name
        }.map {
            it.total
        }