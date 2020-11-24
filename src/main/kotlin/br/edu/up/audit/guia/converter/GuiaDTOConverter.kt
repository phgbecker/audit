package br.edu.up.audit.guia.converter

import br.edu.up.audit.guia.api.dto.GuiaDTO
import br.edu.up.audit.guia.business.objects.GuiaInstancia
import br.edu.up.audit.guia.exception.GuiaInvalidaException

fun GuiaDTO.toInstancias(): List<GuiaInstancia> =
        procedimentos
                ?.map {
                    GuiaInstancia(
                            idadeBeneficiario = idadeBeneficiario,
                            cid = cid,
                            descricao = it.descricao,
                            especialidade = it.especialidade,
                            especialidadePrestador = it.especialidadePrestador,
                            quantidade = it.quantidade,
                            valor = it.valor
                    )
                }
                ?: throw GuiaInvalidaException("A guia informada não contem procedimentos!")