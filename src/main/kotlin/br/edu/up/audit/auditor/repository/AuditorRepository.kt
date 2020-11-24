package br.edu.up.audit.auditor.repository

import br.edu.up.audit.auditor.model.AuditorModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AuditorRepository : JpaRepository<AuditorModel, Long> {

    fun findAllByEmpresaCnpjAndIsAtivoIsTrueOrderByNomeAsc(cnpj: String): List<AuditorModel>

    fun findByEmpresaCnpjAndCpfAndIsAtivoIsTrue(cnpj: String, cpf: String): AuditorModel?

    @Query("""
        SELECT a
        FROM AuditorModel a
        WHERE
            a.empresa.cnpj = :cnpj
        AND (a.cpf = :cpf OR a.email = :email)
        AND a.isAtivo IS TRUE
    """)
    fun findByEmpresaCnpjAndCpfOrEmailAndIsAtivoIsTrue(cnpj: String, cpf: String, email: String): AuditorModel?

    fun findByEmailAndIsAtivoIsTrue(email: String): AuditorModel?
}