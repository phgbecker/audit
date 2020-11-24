package br.edu.up.audit.empresa.repository

import br.edu.up.audit.empresa.model.EmpresaModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmpresaRepository : JpaRepository<EmpresaModel, Long> {

    fun findByCnpj(cnpj: String): EmpresaModel?
}