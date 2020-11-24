package br.edu.up.audit.motor.converter

import br.edu.up.audit.motor.api.dto.MotorDTO
import br.edu.up.audit.motor.model.MotorModel
import br.edu.up.audit.util.toDiaMesAno

fun MotorModel.toDTO(): MotorDTO =
        MotorDTO(
                registros = registros,
                registrosTreino = registrosTreino,
                registrosTeste = registrosTeste,
                acuracia = acuracia,
                sensibilidade = sensibilidade,
                especificidade = especificidade,
                baseHistorica = baseHistorica,
                dataCriacao = dataCriacao.toDiaMesAno()
        )