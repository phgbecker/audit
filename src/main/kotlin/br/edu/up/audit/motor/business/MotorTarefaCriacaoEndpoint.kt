package br.edu.up.audit.motor.business

import software.amazon.awssdk.services.sagemaker.SageMakerClient
import software.amazon.awssdk.services.sagemaker.model.CreateEndpointConfigRequest
import software.amazon.awssdk.services.sagemaker.model.CreateEndpointRequest
import software.amazon.awssdk.services.sagemaker.model.DescribeEndpointRequest
import software.amazon.awssdk.services.sagemaker.model.EndpointStatus.FAILED
import software.amazon.awssdk.services.sagemaker.model.EndpointStatus.IN_SERVICE
import software.amazon.awssdk.services.sagemaker.model.InstanceType.ML_T2_MEDIUM
import software.amazon.awssdk.services.sagemaker.model.ProductionVariant
import java.util.concurrent.TimeUnit

class MotorTarefaCriacaoEndpoint(private val nome: String) : Runnable {

    override fun run() {
        SageMakerClient.builder()
                .build()
                .use {
                    it.createEndpointConfig(
                            CreateEndpointConfigRequest.builder()
                                    .endpointConfigName(nome)
                                    .productionVariants(ProductionVariant.builder()
                                            .modelName(nome)
                                            .instanceType(ML_T2_MEDIUM.toString())
                                            .variantName("AllTraffic")
                                            .initialVariantWeight(1F)
                                            .initialInstanceCount(1)
                                            .build()
                                    ).build()
                    )

                    it.createEndpoint(
                            CreateEndpointRequest.builder()
                                    .endpointConfigName(nome)
                                    .endpointName(nome)
                                    .build()
                    )


                    DescribeEndpointRequest.builder()
                            .endpointName(nome)
                            .build()
                            .aguardarProcessamento(sageMaker = it)
                }
    }

    private fun DescribeEndpointRequest.aguardarProcessamento(sageMaker: SageMakerClient) {
        do {
            TimeUnit.MINUTES.sleep(1)
        } while (sageMaker
                        .describeEndpoint(this)
                        .endpointStatus() !in IN_SERVICE..FAILED
        )
    }
}