package br.edu.up.audit.motor.business.objects

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class MotorParametrosTreinamento(@Value("\${aws.s3.bucket}") val repositorio: String,
                                 @Value("\${aws.s3.base_historica}") val baseHistorica: String,
                                 @Value("\${aws.s3.base_treinamento}") val baseTreinamento: String,
                                 @Value("\${aws.sagemaker.role_arn}") val sageMakerRegra: String,
                                 @Value("\${aws.sagemaker.imagem}") val sageMakerImagem: String,
                                 @Value("\${aws.sagemaker.namespace}") val sageMakerNamespace: String)