package br.edu.up.audit.auditor.converter

import br.edu.up.audit.auditor.api.dto.AuditorDTO
import br.edu.up.audit.auditor.model.AuditorModel

fun List<AuditorModel>.toDTOs(): List<AuditorDTO> =
        map {
            it.toDTO()
        }