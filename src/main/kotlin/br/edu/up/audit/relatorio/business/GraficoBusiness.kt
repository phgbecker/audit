package br.edu.up.audit.relatorio.business

import br.edu.up.audit.relatorio.business.mapper.RegistroGraficoRowMapper
import br.edu.up.audit.relatorio.business.objects.RegistroGrafico
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class GraficoBusiness(private val repositorio: JdbcTemplate) {

    fun guiasPorIdade(cnpj: String, dataInicial: Date? = null, dataFinal: Date? = null): List<RegistroGrafico> =
            repositorio.query("""
                SELECT
                    CONCAT(
                      10 * FLOOR(g.idade_beneficiario/10),
                      '-',
                      10 * FLOOR(g.idade_beneficiario/10) + 10
                    )                          AS indice,
                    g.indicativo_fraude        AS indicativo,
                    COUNT(g.indicativo_fraude) AS total
                FROM
                    guia AS g
                JOIN empresa AS e ON (
                    e.id_empresa = g.id_empresa
                )
                WHERE
                    e.cnpj = ?
                AND (? IS NULL OR g.data_emissao >= ?)
                AND (? IS NULL OR g.data_emissao <= ?)
                AND (g.status_processamento = 'PROCESSADO')	
                GROUP BY
                    indice,
                    indicativo
                ORDER BY
                    indice
                """.trimIndent(),
                    arrayOf(cnpj, dataInicial, dataInicial, dataFinal, dataFinal),
                    RegistroGraficoRowMapper()
            )

    fun guiasPorEspecialidade(cnpj: String, dataInicial: Date? = null, dataFinal: Date? = null): List<RegistroGrafico> =
            repositorio.query("""
                SELECT
                    p.especialidade            AS indice,
                    g.indicativo_fraude        AS indicativo,
                    COUNT(g.indicativo_fraude) AS total
                FROM
                    guia AS g
                JOIN empresa AS e ON (
                    e.id_empresa = g.id_empresa
                )
                JOIN procedimento AS p ON (
                    p.id_guia = g.id_guia
                )
                WHERE
                    e.cnpj = ?
                AND (? IS NULL OR g.data_emissao >= ?)
                AND (? IS NULL OR g.data_emissao <= ?)
                AND (g.status_processamento = 'PROCESSADO')	
                GROUP BY
                    indice,
                    indicativo
                ORDER BY
                    indice
                """.trimIndent(),
                    arrayOf(cnpj, dataInicial, dataInicial, dataFinal, dataFinal),
                    RegistroGraficoRowMapper()
            )

    fun guiasPorPrestador(cnpj: String, dataInicial: Date? = null, dataFinal: Date? = null): List<RegistroGrafico> =
            repositorio.query("""
                SELECT
                    p.especialidade_prestador  AS indice,
                    g.indicativo_fraude        AS indicativo,
                    COUNT(g.indicativo_fraude) AS total
                FROM
                    guia AS g
                JOIN empresa AS e ON (
                    e.id_empresa = g.id_empresa
                )
                JOIN procedimento AS p ON (
                    p.id_guia = g.id_guia
                )
                WHERE
                    e.cnpj = ?
                AND (? IS NULL OR g.data_emissao >= ?)
                AND (? IS NULL OR g.data_emissao <= ?)
                AND (g.status_processamento = 'PROCESSADO')	
                GROUP BY
                    indice,
                    indicativo
                ORDER BY
                    indice
                """.trimIndent(),
                    arrayOf(cnpj, dataInicial, dataInicial, dataFinal, dataFinal),
                    RegistroGraficoRowMapper()
            )

    fun guiasPorTempo(cnpj: String, dataInicial: Date? = null, dataFinal: Date? = null): List<RegistroGrafico> =
            repositorio.query("""
                WITH
                    guias AS (
                        SELECT
                            MONTH(data_emissao) AS mes,
                            indicativo_fraude,
                            COUNT(DISTINCT id_guia) AS qtd
                        FROM guia AS g
                        LEFT JOIN empresa AS e ON (
                            e.id_empresa = g.id_empresa
                        )
                        WHERE
                            (e.cnpj = ?)
                        AND (? IS NULL OR g.data_emissao >= ?)
                        AND (? IS NULL OR g.data_emissao <= ?)
                        AND (g.status_processamento = 'PROCESSADO')
                        GROUP BY
                            mes,
                            indicativo_fraude
                        ORDER BY
                            mes    
                    ),
                    mes AS (
                        SELECT 1 AS numero, 'Janeiro'   AS mes
                        UNION
                        SELECT 2 AS numero, 'Fevereiro' AS mes
                        UNION
                        SELECT 3 AS numero, 'Março'     AS mes
                        UNION
                        SELECT 4 AS numero, 'Abril'     AS mes
                        UNION
                        SELECT 5 AS numero, 'Maio'      AS mes
                        UNION
                        SELECT 6 AS numero, 'Junho'     AS mes
                        UNION
                        SELECT 7 AS numero, 'Julho'     AS mes
                        UNION
                        SELECT 8 AS numero, 'Agosto'    AS mes
                        UNION
                        SELECT 9 AS numero, 'Setembro'  AS mes
                        UNION
                        SELECT 10 AS numero, 'Outubro'  AS mes
                        UNION
                        SELECT 11 AS numero, 'Novembro' AS mes
                        UNION
                        SELECT 12 AS numero, 'Dezembro' AS mes
                    ),
                    indicativo AS (
                        SELECT 'SIM'          AS descricao
                        UNION
                        SELECT 'NAO'          AS descricao
                        UNION
                        SELECT 'INCONCLUSIVO' AS descricao
                    ),
                    base_final AS (
                        SELECT *
                        FROM mes
                        CROSS JOIN indicativo
                    )
                SELECT
                    bf.mes           AS indice,
                    bf.descricao     AS indicativo,    
                    COALESCE(qtd, 0) AS total
                FROM base_final bf
                LEFT JOIN guias AS g ON (
                        bf.numero = g.mes
                    AND bf.descricao = g.indicativo_fraude
                )
                """.trimIndent(),
                    arrayOf(cnpj, dataInicial, dataInicial, dataFinal, dataFinal),
                    RegistroGraficoRowMapper()
            )
}