package br.edu.up.audit.empresa.facade

import br.edu.up.audit.empresa.api.dto.EmpresaDTO
import br.edu.up.audit.empresa.business.EmpresaBusiness
import br.edu.up.audit.empresa.business.objects.EmpresaRetornoPesquisa.Encontrado
import br.edu.up.audit.empresa.business.objects.EmpresaRetornoPesquisa.NaoEncontrado
import br.edu.up.audit.empresa.converter.toDTO
import br.edu.up.audit.empresa.exception.EmpresaNaoEncontradaException
import br.edu.up.audit.util.toNumero
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmpresaFacade(private val empresaBusiness: EmpresaBusiness) {

    fun editar(cnpj: String, entrada: EmpresaDTO): EmpresaDTO =
            empresaBusiness.obter(cnpj)
                    .run {
                        when (this) {
                            is Encontrado ->
                                empresaBusiness.editar(
                                        empresa = empresa.copy(
                                                razaoSocial = entrada.razaoSocial,
                                                nomeFantasia = entrada.nomeFantasia,
                                                cep = entrada.cep.toNumero(),
                                                endereco = entrada.endereco,
                                                numero = entrada.numero,
                                                bairro = entrada.bairro,
                                                complemento = entrada.complemento,
                                                cidade = entrada.cidade,
                                                estado = entrada.estado,
                                                telefone = entrada.telefone,
                                                email = entrada.email,
                                                registroANS = entrada.registroANS.toNumero(),
                                                dataEdicao = Date())
                                ).toDTO()

                            is NaoEncontrado ->
                                throw EmpresaNaoEncontradaException(mensagem)
                        }
                    }

    fun obter(cnpj: String): EmpresaDTO =
            empresaBusiness.obter(cnpj)
                    .run {
                        when (this) {
                            is Encontrado ->
                                empresa.toDTO()

                            is NaoEncontrado ->
                                throw EmpresaNaoEncontradaException(mensagem)
                        }
                    }
}