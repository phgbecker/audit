package br.edu.up.audit.relatorio.api.dto

import br.edu.up.audit.relatorio.business.objects.RegistroIndicativo

data class VisaoGeralDTO(
        val indicador: RegistroIndicativo?,
        val relatorio: RelatorioDTO
)