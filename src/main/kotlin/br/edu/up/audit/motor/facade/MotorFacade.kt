package br.edu.up.audit.motor.facade

import br.edu.up.audit.motor.api.dto.MotorDTO
import br.edu.up.audit.motor.business.MotorBusiness
import br.edu.up.audit.motor.business.objects.MotorRetornoPesquisa.Encontrado
import br.edu.up.audit.motor.business.objects.MotorRetornoPesquisa.NaoEncontrado
import br.edu.up.audit.motor.converter.toDTO
import br.edu.up.audit.motor.converter.toDTOs
import br.edu.up.audit.motor.exception.MotorNaoEncontradoException
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.SdkBytes.fromByteArray
import software.amazon.awssdk.services.sagemakerruntime.SageMakerRuntimeClient
import software.amazon.awssdk.services.sagemakerruntime.model.InvokeEndpointRequest

@Service
class MotorFacade(private val motorBusiness: MotorBusiness) {

    fun listar(cnpj: String): List<MotorDTO> =
            motorBusiness.listar(cnpj).toDTOs()

    fun obter(cnpj: String): MotorDTO =
            motorBusiness.obter(cnpj)
                    .run {
                        when (this) {
                            is Encontrado ->
                                motor.toDTO()

                            is NaoEncontrado ->
                                throw MotorNaoEncontradoException(mensagem)
                        }
                    }

    fun inferir(cnpj: String, arquivo: ByteArray): ByteArray =
            motorBusiness.obter(cnpj)
                    .run {
                        when (this) {
                            is Encontrado ->
                                SageMakerRuntimeClient.builder()
                                        .build()
                                        .use {
                                            it.invokeEndpoint(
                                                    InvokeEndpointRequest.builder()
                                                            .endpointName(motor.endpoint)
                                                            .contentType("text/csv")
                                                            .body(fromByteArray(arquivo))
                                                            .build()
                                            ).body().asByteArray()
                                        }


                            is NaoEncontrado ->
                                throw MotorNaoEncontradoException(mensagem)
                        }
                    }

    fun treinar(cnpj: String, arquivo: ByteArray) =
            motorBusiness.agendarTreinamento(cnpj, arquivo)
}