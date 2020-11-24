package br.edu.up.audit.guia.converter

import br.edu.up.audit.auditor.model.AuditorModel
import br.edu.up.audit.empresa.model.EmpresaModel
import br.edu.up.audit.guia.api.dto.ProcedimentoDTO
import br.edu.up.audit.guia.model.GuiaModel
import br.edu.up.audit.guia.model.ProcedimentoModel

fun List<ProcedimentoDTO>.toModel(empresa: EmpresaModel, auditor: AuditorModel, guia: GuiaModel): List<ProcedimentoModel> =
        map {
            ProcedimentoModel(
                    codigo = it.codigo,
                    descricao = it.descricao,
                    especialidade = it.especialidade,
                    especialidadePrestador = it.especialidadePrestador,
                    quantidade = it.quantidade,
                    valor = it.valor,
                    empresa = empresa,
                    auditor = auditor,
                    guia = guia
            )
        }
