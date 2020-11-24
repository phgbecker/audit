package br.edu.up.audit.relatorio.business.mapper

import br.edu.up.audit.relatorio.business.objects.RegistroIndicativo
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class RegistroIndicativoRowMapper : RowMapper<RegistroIndicativo> {

    override fun mapRow(resultSet: ResultSet, row: Int): RegistroIndicativo =
            RegistroIndicativo(
                    beneficiarios = resultSet.getInt("beneficiarios"),
                    guias = resultSet.getInt("guias"),
                    procedimentos = resultSet.getInt("procedimentos"),
                    guiasSemIndicativo = resultSet.getInt("guiasSemIndicativo"),
                    guiasComIndicativo = resultSet.getInt("guiasComIndicativo"),
                    guiasInconclusivas = resultSet.getInt("guiasInconclusivas")
            )
}