package br.edu.up.audit.motor.converter

import br.edu.up.audit.motor.api.dto.MotorDTO
import br.edu.up.audit.motor.model.MotorModel

fun List<MotorModel>.toDTOs(): List<MotorDTO> =
        map {
            it.toDTO()
        }