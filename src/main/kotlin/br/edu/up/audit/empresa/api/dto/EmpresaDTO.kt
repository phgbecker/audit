package br.edu.up.audit.empresa.api.dto

data class EmpresaDTO(
        val cnpj: String,
        val razaoSocial: String,
        val nomeFantasia: String,
        val cep: String,
        val endereco: String,
        val numero: Int,
        val bairro: String,
        val complemento: String,
        val cidade: String,
        val estado: String,
        val telefone: String,
        val email: String,
        val registroANS: String
)