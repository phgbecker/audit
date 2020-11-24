package br.edu.up.audit.motor.converter

import br.edu.up.audit.motor.business.objects.MotorMetricaModelo

fun List<MotorMetricaModelo>.acuracia(): Double =
        first {
            it.nome == "Acuracia"
        }.valor

fun List<MotorMetricaModelo>.sensibilidade(): Double =
        first {
            it.nome == "Sensibilidade"
        }.valor

fun List<MotorMetricaModelo>.especificidade(): Double =
        first {
            it.nome == "Especificidade"
        }.valor

fun List<MotorMetricaModelo>.registros(): Long =
        registrosTreino() + registrosTeste()

fun List<MotorMetricaModelo>.registrosTreino(): Long =
        first {
            it.nome == "BaseTreino"
        }.valor.toLong()

fun List<MotorMetricaModelo>.registrosTeste(): Long =
        first {
            it.nome == "BaseTeste"
        }.valor.toLong()