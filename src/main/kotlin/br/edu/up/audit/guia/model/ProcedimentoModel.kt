package br.edu.up.audit.guia.model

import br.edu.up.audit.auditor.model.AuditorModel
import br.edu.up.audit.empresa.model.EmpresaModel
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "procedimento")
data class ProcedimentoModel(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_procedimento", nullable = false)
        val id: Long? = null,

        @Column(name = "codigo", nullable = false)
        val codigo: Long,

        @Column(name = "descricao", nullable = false)
        val descricao: String,

        @Column(name = "especialidade", nullable = false)
        val especialidade: String,

        @Column(name = "especialidade_prestador", nullable = false)
        val especialidadePrestador: String,

        @Column(name = "quantidade", nullable = false)
        val quantidade: Int,

        @Column(name = "valor", nullable = false)
        val valor: BigDecimal,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_empresa", nullable = false, referencedColumnName = "id_empresa")
        val empresa: EmpresaModel,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_auditor", nullable = false, referencedColumnName = "id_auditor")
        val auditor: AuditorModel,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_guia", nullable = false, referencedColumnName = "id_guia")
        val guia: GuiaModel
)