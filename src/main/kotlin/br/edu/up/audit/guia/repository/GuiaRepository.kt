package br.edu.up.audit.guia.repository

import br.edu.up.audit.guia.model.GuiaModel
import br.edu.up.audit.guia.model.StatusProcessamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GuiaRepository : JpaRepository<GuiaModel, Long> {

    @Query(value = """
            SELECT g
            FROM GuiaModel AS g
            WHERE
                g.empresa.cnpj = :cnpj
            AND (:dataInicial IS NULL OR g.dataEmissao >= :dataInicial)
            AND (:dataFinal IS NULL OR g.dataEmissao <= :dataFinal)
            AND (:status IS NULL OR g.statusProcessamento = :status)
            ORDER BY g.dataCriacao DESC""")
    fun findAllByEmpresaCnpjAndSituacaoOrderByDataCriacaoDesc(cnpj: String, dataInicial: Date?, dataFinal: Date?, status: StatusProcessamento?): List<GuiaModel>

    fun findByEmpresaCnpjAndCodigo(cnpj: String, codigo: Long): GuiaModel?
}