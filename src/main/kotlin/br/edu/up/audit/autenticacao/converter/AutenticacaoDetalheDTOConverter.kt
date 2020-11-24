package br.edu.up.audit.autenticacao.converter

import br.edu.up.audit.autenticacao.business.objects.AutenticacaoDetalheDTO
import com.fasterxml.jackson.databind.ObjectMapper

fun AutenticacaoDetalheDTO.toJSON(): String =
        ObjectMapper().writeValueAsString(this)
