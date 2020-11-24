package br.edu.up.audit.util

import java.text.SimpleDateFormat
import java.util.*

fun Date.toDiaMesAno(): String =
        SimpleDateFormat("dd/MM/yyyy").format(this)

fun Date.toAnoMesDia(): String =
        SimpleDateFormat("yyyy-MM-dd").format(this)

fun Date.toAnoMesDiaHora(): String =
        SimpleDateFormat("yyyy-MM-dd-hhmmss").format(this)