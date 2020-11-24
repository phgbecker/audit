package br.edu.up.audit.relatorio.business.objects

data class RegistroIndicativo(
        val beneficiarios: Int,
        val guias: Int,
        val procedimentos: Int,
        val guiasSemIndicativo: Int,
        val guiasComIndicativo: Int,
        val guiasInconclusivas: Int
)