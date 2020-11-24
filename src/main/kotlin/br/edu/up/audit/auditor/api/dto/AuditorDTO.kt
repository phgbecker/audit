package br.edu.up.audit.auditor.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY

data class AuditorDTO(
        val cpf: String,
        val nome: String,
        val email: String,
        val telefone: String,
        @JsonProperty(access = WRITE_ONLY) val senha: String? = null,
        val administrador: Boolean?
)