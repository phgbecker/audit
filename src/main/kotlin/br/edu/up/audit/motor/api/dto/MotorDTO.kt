package br.edu.up.audit.motor.api.dto

data class MotorDTO(
        val registros: Long,
        val registrosTreino: Long,
        val registrosTeste: Long,
        val acuracia: Double,
        val sensibilidade: Double,
        val especificidade: Double,
        val verdadeiroPositivo: Int? = null,
        val falsoPositivo: Int? = null,
        val verdadeiroNegativo: Int? = null,
        val falsoNegativo: Int? = null,
        val baseHistorica: String,
        val dataCriacao: String
)