package br.edu.up.audit.guia.business.objects

import br.edu.up.audit.guia.model.GuiaModel

sealed class GuiaResultadoPesquisa {
    class Encontrada(val guia: GuiaModel) : GuiaResultadoPesquisa()
    class NaoEncontrada(val mensagem: String) : GuiaResultadoPesquisa()
}