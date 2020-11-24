package br.edu.up.audit.guia.converter

import br.edu.up.audit.guia.api.dto.GuiaDTO
import br.edu.up.audit.guia.model.GuiaModel
import br.edu.up.audit.util.toAnoMesDia
import br.edu.up.audit.util.toDiaMesAno

fun GuiaModel.toDTO(): GuiaDTO =
        GuiaDTO(
                codigo = codigo,
                codigoBeneficiario = codigoBeneficiario,
                idadeBeneficiario = idadeBeneficiario,
                cid = cid,
                dataEmissao = dataEmissao.toAnoMesDia(),
                dataCriacao = dataCriacao.toDiaMesAno(),
                statusProcessamento = statusProcessamento,
                indicativoFraude = indicativoFraude,
                scoreIndicativo = scoreIndicativo,
                procedimentos = procedimentos?.map { it.toDTO() }
        )

fun GuiaModel.toDTOResumido(): GuiaDTO =
        GuiaDTO(
                codigo = codigo,
                codigoBeneficiario = codigoBeneficiario,
                idadeBeneficiario = idadeBeneficiario,
                cid = cid,
                auditor = auditor.nome.substringBefore(" "),
                dataEmissao = dataEmissao.toDiaMesAno(),
                dataCriacao = dataCriacao.toDiaMesAno(),
                statusProcessamento = statusProcessamento,
                scoreIndicativo = scoreIndicativo,
                indicativoFraude = indicativoFraude
        )