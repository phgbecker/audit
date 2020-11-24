package br.edu.up.audit.guia.converter

import br.edu.up.audit.guia.api.dto.GuiaDTO
import br.edu.up.audit.guia.model.GuiaModel

fun List<GuiaModel>.toDTOsResumido(): List<GuiaDTO> =
        map {
            it.toDTOResumido()
        }