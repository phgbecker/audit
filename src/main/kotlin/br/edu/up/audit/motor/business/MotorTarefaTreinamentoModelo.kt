package br.edu.up.audit.motor.business

import software.amazon.awssdk.services.sagemaker.SageMakerClient
import software.amazon.awssdk.services.sagemaker.model.*
import software.amazon.awssdk.services.sagemaker.model.TrainingInputMode.FILE
import software.amazon.awssdk.services.sagemaker.model.TrainingInstanceType.ML_M4_XLARGE
import software.amazon.awssdk.services.sagemaker.model.TrainingJobStatus.COMPLETED
import software.amazon.awssdk.services.sagemaker.model.TrainingJobStatus.FAILED
import java.util.concurrent.TimeUnit

class MotorTarefaTreinamentoModelo(private val nome: String,
                                   private val url: String,
                                   private val imagem: String,
                                   private val regraExecucao: String) : Runnable {

    override fun run() {
        SageMakerClient.builder()
                .build()
                .use {
                    it.createTrainingJob(
                            CreateTrainingJobRequest.builder()
                                    .trainingJobName(nome)
                                    .algorithmSpecification(
                                            AlgorithmSpecification.builder()
                                                    .trainingImage(imagem)
                                                    .trainingInputMode(FILE)
                                                    .metricDefinitions(
                                                            listOf(
                                                                    MetricDefinition.builder()
                                                                            .name("Acuracia")
                                                                            .regex("accuracy: (0.*?);")
                                                                            .build(),
                                                                    MetricDefinition.builder()
                                                                            .name("Sensibilidade")
                                                                            .regex("recall: (0.*?);")
                                                                            .build(),
                                                                    MetricDefinition.builder()
                                                                            .name("Especificidade")
                                                                            .regex("precision: (0.*?);")
                                                                            .build(),
                                                                    MetricDefinition.builder()
                                                                            .name("BaseTreino")
                                                                            .regex("data_for_train: ([0-9]*?);")
                                                                            .build(),
                                                                    MetricDefinition.builder()
                                                                            .name("BaseTeste")
                                                                            .regex("data_for_test: ([0-9]*?);")
                                                                            .build()
                                                            )
                                                    ).build()
                                    ).roleArn(regraExecucao)
                                    .resourceConfig(
                                            ResourceConfig.builder()
                                                    .instanceType(ML_M4_XLARGE)
                                                    .instanceCount(1)
                                                    .volumeSizeInGB(1)
                                                    .build()
                                    ).checkpointConfig(
                                            CheckpointConfig.builder()
                                                    .s3Uri(url)
                                                    .build()
                                    )
                                    .outputDataConfig(
                                            OutputDataConfig.builder()
                                                    .s3OutputPath(url)
                                                    .build()
                                    ).stoppingCondition(
                                            StoppingCondition.builder()
                                                    .maxRuntimeInSeconds(1200)
                                                    .build()
                                    ).build()
                    )


                    DescribeTrainingJobRequest.builder()
                            .trainingJobName(nome)
                            .build()
                            .aguardarProcessamento(sageMaker = it)
                }
    }

    private fun DescribeTrainingJobRequest.aguardarProcessamento(sageMaker: SageMakerClient) {
        do {
            TimeUnit.MINUTES.sleep(1)
        } while (sageMaker.describeTrainingJob(this)
                        .trainingJobStatus() !in COMPLETED..FAILED
        )
    }
}