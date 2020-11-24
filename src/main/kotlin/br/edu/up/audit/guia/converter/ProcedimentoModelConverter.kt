package br.edu.up.audit.guia.converter

import br.edu.up.audit.guia.api.dto.ProcedimentoDTO
import br.edu.up.audit.guia.model.ProcedimentoModel

fun ProcedimentoModel.toDTO(): ProcedimentoDTO =
        ProcedimentoDTO(
                codigo = codigo,
                descricao = descricao,
                especialidade = especialidade,
                especialidadePrestador = especialidadePrestador,
                quantidade = quantidade,
                valor = valor
        )