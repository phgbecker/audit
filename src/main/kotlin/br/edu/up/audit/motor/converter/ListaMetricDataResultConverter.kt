package br.edu.up.audit.motor.converter

import br.edu.up.audit.motor.business.objects.MotorMetricaModelo
import br.edu.up.audit.motor.exception.MotorFalhaExecucaoException
import software.amazon.awssdk.services.cloudwatch.model.MetricDataResult

fun List<MetricDataResult>.toMetricas(): List<MotorMetricaModelo> =
        map {
            MotorMetricaModelo(
                    nome = it.label(),
                    valor = it.values()?.firstOrNull()
                            ?: throw MotorFalhaExecucaoException("Não foi possível obter a métrica ${it.label()}")
            )
        }