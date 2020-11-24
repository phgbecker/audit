package br.edu.up.audit.auditor.model

import br.edu.up.audit.empresa.model.EmpresaModel
import br.edu.up.audit.guia.model.GuiaModel
import java.util.*
import javax.persistence.*

@Entity
@Table(
        name = "auditor",
        uniqueConstraints = [
            UniqueConstraint(columnNames = ["id_auditor", "cpf", "ativo"]),
            UniqueConstraint(columnNames = ["id_auditor", "email", "ativo"])
        ]
)
data class AuditorModel(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_auditor", nullable = false)
        val id: Long? = null,

        @Column(name = "cpf", nullable = false)
        val cpf: String,

        @Column(name = "nome", nullable = false)
        val nome: String,

        @Column(name = "email", nullable = false)
        val email: String,

        @Column(name = "telefone", nullable = false)
        val telefone: String,

        @Column(name = "senha", nullable = false)
        val senha: String,

        @Column(name = "administrador", nullable = false)
        val isAdministrador: Boolean,

        @Column(name = "ativo", nullable = false)
        val isAtivo: Boolean,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "data_criacao", nullable = false)
        val dataCriacao: Date,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "data_edicao", nullable = false)
        val dataEdicao: Date,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_empresa", nullable = false, referencedColumnName = "id_empresa")
        val empresa: EmpresaModel,

        @OneToMany(mappedBy = "auditor", fetch = FetchType.LAZY)
        val guias: List<GuiaModel>? = null
)