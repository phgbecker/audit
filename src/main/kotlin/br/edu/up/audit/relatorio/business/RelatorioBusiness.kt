package br.edu.up.audit.relatorio.business

import br.edu.up.audit.relatorio.business.mapper.RegistroIndicativoRowMapper
import br.edu.up.audit.relatorio.business.objects.RegistroIndicativo
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class RelatorioBusiness(private val repositorio: JdbcTemplate) {

    fun geral(cnpj: String): RegistroIndicativo? =
            repositorio.queryForObject("""
            SELECT
                COUNT(DISTINCT g.codigo_beneficiario) AS beneficiarios,
                COUNT(DISTINCT g.id_guia)             AS guias,
                COUNT(p.id_procedimento)              AS procedimentos,
                COUNT(DISTINCT(IF(g.indicativo_fraude = 'NAO', g.id_guia, NULL)))          AS guiasSemIndicativo,
                COUNT(DISTINCT(IF(g.indicativo_fraude = 'SIM', g.id_guia, NULL)))          AS guiasComIndicativo,
                COUNT(DISTINCT(IF(g.indicativo_fraude = 'INCONCLUSIVO', g.id_guia, NULL))) AS guiasInconclusivas
             FROM
                guia AS g
            JOIN empresa AS e ON (
                e.id_empresa = g.id_empresa
            )
            LEFT JOIN procedimento AS p ON (
                p.id_guia = g.id_guia
            )
            WHERE
                e.cnpj = ?
            AND (g.status_processamento = 'PROCESSADO')
            """.trimIndent(),
                    arrayOf(cnpj),
                    RegistroIndicativoRowMapper()
            )
}