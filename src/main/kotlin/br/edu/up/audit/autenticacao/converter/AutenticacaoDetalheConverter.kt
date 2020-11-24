package br.edu.up.audit.autenticacao.converter

import br.edu.up.audit.auditor.converter.toDTO
import br.edu.up.audit.autenticacao.business.objects.AutenticacaoDetalhe
import br.edu.up.audit.autenticacao.business.objects.AutenticacaoDetalheDTO
import br.edu.up.audit.empresa.converter.toDTO

fun AutenticacaoDetalhe.toDTO(): AutenticacaoDetalheDTO =
        AutenticacaoDetalheDTO(
                auditor = auditor.toDTO(),
                empresa = auditor.empresa.toDTO()
        )