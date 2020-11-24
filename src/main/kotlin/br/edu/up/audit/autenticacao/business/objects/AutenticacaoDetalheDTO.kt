package br.edu.up.audit.autenticacao.business.objects

import br.edu.up.audit.auditor.api.dto.AuditorDTO
import br.edu.up.audit.empresa.api.dto.EmpresaDTO

data class AutenticacaoDetalheDTO(
        val auditor: AuditorDTO,
        val empresa: EmpresaDTO? = null
)