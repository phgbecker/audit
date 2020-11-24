package br.edu.up.audit.empresa.business.objects

import br.edu.up.audit.empresa.model.EmpresaModel

sealed class EmpresaRetornoPesquisa {
    class Encontrado(val empresa: EmpresaModel) : EmpresaRetornoPesquisa()
    class NaoEncontrado(val mensagem: String) : EmpresaRetornoPesquisa()
}