package br.edu.up.audit.auditor.business.objects

import br.edu.up.audit.auditor.model.AuditorModel

sealed class AuditorRetornoPesquisa {
    class Encontrado(val auditor: AuditorModel) : AuditorRetornoPesquisa()
    class NaoEncontrado(val mensagem: String) : AuditorRetornoPesquisa()
}