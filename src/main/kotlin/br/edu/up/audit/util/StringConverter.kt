package br.edu.up.audit.util

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.text.SimpleDateFormat
import java.util.*

fun String.toNumero(): String =
        replace("[^\\d]".toRegex(), "")

fun String.toDate(): Date? =
        if (isEmpty()) null else SimpleDateFormat("yyyy-MM-dd").parse(this)

fun String.toBCrypt(): String =
        BCryptPasswordEncoder().encode(this)