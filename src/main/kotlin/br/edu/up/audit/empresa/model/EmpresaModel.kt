package br.edu.up.audit.empresa.model

import br.edu.up.audit.auditor.model.AuditorModel
import br.edu.up.audit.motor.model.MotorModel
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "empresa")
data class EmpresaModel(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_empresa")
        val id: Long? = null,

        @Column(name = "cnpj", unique = true, nullable = false)
        val cnpj: String,

        @Column(name = "razao_social", nullable = false)
        val razaoSocial: String,

        @Column(name = "nome_fantasia", nullable = false)
        val nomeFantasia: String,

        @Column(name = "cep", nullable = false)
        val cep: String,

        @Column(name = "endereco", nullable = false)
        val endereco: String,

        @Column(name = "numero", nullable = false)
        val numero: Int,

        @Column(name = "bairro", nullable = false)
        val bairro: String,

        @Column(name = "complemento", nullable = false)
        val complemento: String,

        @Column(name = "cidade", nullable = false)
        val cidade: String,

        @Column(name = "estado", nullable = false)
        val estado: String,

        @Column(name = "telefone", nullable = false)
        val telefone: String,

        @Column(name = "email", nullable = false)
        val email: String,

        @Column(name = "registro_ans", nullable = false)
        val registroANS: String,

        @Column(name = "ativo", nullable = false)
        val isAtivo: Boolean,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "data_criacao", nullable = false)
        val dataCriacao: Date,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "data_edicao", nullable = false)
        val dataEdicao: Date,

        @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
        val auditores: List<AuditorModel>? = null,

        @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
        val motores: List<MotorModel>? = null
)