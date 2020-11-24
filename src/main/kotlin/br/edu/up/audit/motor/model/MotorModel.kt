package br.edu.up.audit.motor.model

import br.edu.up.audit.auditor.model.AuditorModel
import br.edu.up.audit.empresa.model.EmpresaModel
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "motor")
data class MotorModel(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_motor", nullable = false)
        val id: Long? = null,

        @Column(name = "registros", nullable = false)
        val registros: Long,

        @Column(name = "registros_treino", nullable = false)
        val registrosTreino: Long,

        @Column(name = "registros_teste", nullable = false)
        val registrosTeste: Long,

        @Column(name = "acuracia", nullable = false)
        val acuracia: Double,

        @Column(name = "sensibilidade", nullable = false)
        val sensibilidade: Double,

        @Column(name = "especificidade", nullable = false)
        val especificidade: Double,

        @Column(name = "verdadeiro_positivo")
        val verdadeiroPositivo: Int? = null,

        @Column(name = "falso_positivo")
        val falsoPositivo: Int? = null,

        @Column(name = "verdadeiro_negativo")
        val verdadeiroNegativo: Int? = null,

        @Column(name = "falso_negativo")
        val falsoNegativo: Int? = null,

        @Column(name = "base_historica", nullable = false)
        val baseHistorica: String,

        @Column(name = "endpoint", nullable = false)
        val endpoint: String,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "data_criacao", nullable = false)
        val dataCriacao: Date,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_empresa", nullable = false, referencedColumnName = "id_empresa")
        val empresa: EmpresaModel,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_auditor", nullable = false, referencedColumnName = "id_auditor")
        val auditorModel: AuditorModel
)