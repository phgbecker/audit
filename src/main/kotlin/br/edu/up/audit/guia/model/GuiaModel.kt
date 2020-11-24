package br.edu.up.audit.guia.model

import br.edu.up.audit.auditor.model.AuditorModel
import br.edu.up.audit.empresa.model.EmpresaModel
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.*

@Entity
@Table(
        name = "guia",
        uniqueConstraints = [
            UniqueConstraint(columnNames = ["id_empresa", "codigo"])
        ]
)
data class GuiaModel(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_guia")
        val id: Long? = null,

        @Column(name = "codigo", nullable = false)
        val codigo: Long,

        @Column(name = "codigo_beneficiario", nullable = false)
        val codigoBeneficiario: Long,

        @Column(name = "idade_beneficiario", nullable = false)
        val idadeBeneficiario: Int,

        @Column(name = "cid", nullable = false)
        val cid: String,

        @Temporal(TemporalType.DATE)
        @Column(name = "data_emissao", nullable = false)
        val dataEmissao: Date,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "data_processamento")
        val dataProcessamento: Date? = null,

        @Enumerated(EnumType.STRING)
        @Column(name = "status_processamento", nullable = false)
        val statusProcessamento: StatusProcessamento,

        @Enumerated(EnumType.STRING)
        @Column(name = "indicativo_fraude")
        val indicativoFraude: IndicativoFraude? = null,

        @Column(name = "score_indicativo")
        val scoreIndicativo: Float? = null,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "data_criacao", nullable = false)
        val dataCriacao: Date,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_empresa", nullable = false, referencedColumnName = "id_empresa")
        val empresa: EmpresaModel,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_auditor", nullable = false, referencedColumnName = "id_auditor")
        val auditor: AuditorModel,

        @OneToMany(mappedBy = "guia", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
        @OnDelete(action = OnDeleteAction.CASCADE)
        var procedimentos: List<ProcedimentoModel>? = null
)