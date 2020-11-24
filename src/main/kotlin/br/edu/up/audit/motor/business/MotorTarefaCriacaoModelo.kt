package br.edu.up.audit.motor.business

import software.amazon.awssdk.services.sagemaker.SageMakerClient
import software.amazon.awssdk.services.sagemaker.model.ContainerDefinition
import software.amazon.awssdk.services.sagemaker.model.ContainerMode.SINGLE_MODEL
import software.amazon.awssdk.services.sagemaker.model.CreateModelRequest

class MotorTarefaCriacaoModelo(private val nome: String,
                               private val url: String,
                               private val imagem: String,
                               private val regraExecucao: String) : Runnable {

    override fun run() {
        SageMakerClient.builder()
                .build()
                .use {
                    it.createModel(
                            CreateModelRequest.builder()
                                    .modelName(nome)
                                    .executionRoleArn(regraExecucao)
                                    .primaryContainer(
                                            ContainerDefinition.builder()
                                                    .modelDataUrl("$url/output/model.tar.gz")
                                                    .image(imagem)
                                                    .mode(SINGLE_MODEL)
                                                    .build()
                                    ).build()
                    )
                }
    }
}