package br.edu.up.audit.motor.repository

import br.edu.up.audit.motor.model.MotorModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MotorRepository : JpaRepository<MotorModel, Long> {

    fun findFirstByEmpresaCnpjOrderByDataCriacaoDesc(cnpj: String): MotorModel?

    fun findAllByEmpresaCnpjOrderByDataCriacaoDesc(cnpj: String): List<MotorModel>
}