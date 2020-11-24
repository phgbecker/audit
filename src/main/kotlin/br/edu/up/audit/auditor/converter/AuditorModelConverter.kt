package br.edu.up.audit.auditor.converter

import br.edu.up.audit.auditor.api.dto.AuditorDTO
import br.edu.up.audit.auditor.model.AuditorModel

fun AuditorModel.toDTO(): AuditorDTO =
        AuditorDTO(
                cpf = cpf,
                nome = nome,
                email = email,
                telefone = telefone,
                administrador = isAdministrador
        )