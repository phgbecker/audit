package br.edu.up.audit.relatorio.business.mapper

import br.edu.up.audit.relatorio.business.objects.RegistroGrafico
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class RegistroGraficoRowMapper : RowMapper<RegistroGrafico> {

    override fun mapRow(resultSet: ResultSet, row: Int): RegistroGrafico =
            RegistroGrafico(
                    indice = resultSet.getString("indice"),
                    indicativo = resultSet.getString("indicativo"),
                    total = resultSet.getInt("total")
            )
}