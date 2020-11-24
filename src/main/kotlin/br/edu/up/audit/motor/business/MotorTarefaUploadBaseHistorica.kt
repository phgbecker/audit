package br.edu.up.audit.motor.business

import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest

class MotorTarefaUploadBaseHistorica(private val nome: String,
                                     private val arquivo: ByteArray,
                                     private val repositorio: String,
                                     private val baseHistorica: String) : Runnable {

    override fun run() {
        S3Client.builder()
                .build()
                .use {
                    it.putObject(
                            PutObjectRequest.builder()
                                    .bucket(repositorio)
                                    .contentType("text/csv")
                                    .key(baseHistorica)
                                    .build(),
                            RequestBody.fromBytes(arquivo)
                    )

                    it.putObject(
                            PutObjectRequest.builder()
                                    .bucket(repositorio)
                                    .contentType("text/csv")
                                    .key(nome)
                                    .build(),
                            RequestBody.fromBytes(arquivo)
                    )
                }
    }
}