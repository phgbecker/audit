package br.edu.up.audit.motor.business

import br.edu.up.audit.motor.business.objects.MotorMetricaModelo
import br.edu.up.audit.motor.converter.toMetricas
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient
import software.amazon.awssdk.services.cloudwatch.model.*
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.Callable

class MotorTarefaObtencaoMetricasModelo(private val nome: String,
                                        private val namespace: String) : Callable<List<MotorMetricaModelo>> {

    override fun call(): List<MotorMetricaModelo> =
            CloudWatchClient.builder()
                    .build()
                    .use {
                        it.getMetricData(
                                GetMetricDataRequest.builder()
                                        .maxDatapoints(100)
                                        .startTime(Instant.now().minus(25, ChronoUnit.MINUTES))
                                        .endTime(Instant.now())
                                        .metricDataQueries(
                                                listOf(
                                                        MetricDataQuery.builder()
                                                                .metricStat(
                                                                        MetricStat.builder()
                                                                                .stat("Maximum")
                                                                                .period(180)
                                                                                .metric(
                                                                                        Metric.builder()
                                                                                                .metricName("Acuracia")
                                                                                                .namespace(namespace)
                                                                                                .dimensions(Dimension.builder()
                                                                                                        .name("TrainingJobName")
                                                                                                        .value(nome)
                                                                                                        .build()
                                                                                                ).build()
                                                                                ).build()
                                                                ).id("m1").returnData(true).build(),
                                                        MetricDataQuery.builder()
                                                                .metricStat(
                                                                        MetricStat.builder()
                                                                                .stat("Maximum")
                                                                                .period(180)
                                                                                .metric(
                                                                                        Metric.builder()
                                                                                                .metricName("Sensibilidade")
                                                                                                .namespace(namespace)
                                                                                                .dimensions(Dimension.builder()
                                                                                                        .name("TrainingJobName")
                                                                                                        .value(nome)
                                                                                                        .build()
                                                                                                ).build()
                                                                                ).build()
                                                                ).id("m2").returnData(true).build(),
                                                        MetricDataQuery.builder()
                                                                .metricStat(
                                                                        MetricStat.builder()
                                                                                .stat("Maximum")
                                                                                .period(180)
                                                                                .metric(
                                                                                        Metric.builder()
                                                                                                .metricName("Especificidade")
                                                                                                .namespace(namespace)
                                                                                                .dimensions(Dimension.builder()
                                                                                                        .name("TrainingJobName")
                                                                                                        .value(nome)
                                                                                                        .build()
                                                                                                ).build()
                                                                                ).build()
                                                                ).id("m3").returnData(true).build(),
                                                        MetricDataQuery.builder()
                                                                .metricStat(
                                                                        MetricStat.builder()
                                                                                .stat("Maximum")
                                                                                .period(180)
                                                                                .metric(
                                                                                        Metric.builder()
                                                                                                .metricName("BaseTreino")
                                                                                                .namespace(namespace)
                                                                                                .dimensions(Dimension.builder()
                                                                                                        .name("TrainingJobName")
                                                                                                        .value(nome)
                                                                                                        .build()
                                                                                                ).build()
                                                                                ).build()
                                                                ).id("m4").returnData(true).build(),
                                                        MetricDataQuery.builder()
                                                                .metricStat(
                                                                        MetricStat.builder()
                                                                                .stat("Maximum")
                                                                                .period(180)
                                                                                .metric(
                                                                                        Metric.builder()
                                                                                                .metricName("BaseTeste")
                                                                                                .namespace(namespace)
                                                                                                .dimensions(Dimension.builder()
                                                                                                        .name("TrainingJobName")
                                                                                                        .value(nome)
                                                                                                        .build()
                                                                                                ).build()
                                                                                ).build()
                                                                ).id("m5").returnData(true).build()
                                                )
                                        ).build()
                        ).metricDataResults().toMetricas()
                    }
}