package br.edu.up.audit.guia.business.objects

sealed class GuiaRetornoProcessamento {
    class Processado(val resultado: GuiaResultadoClassificacao) : GuiaRetornoProcessamento()
    class NaoProcessado(val mensagem: String) : GuiaRetornoProcessamento()
}