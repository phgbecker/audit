package br.edu.up.audit.autenticacao.exception

class SessaoInvalidaException(val mensagem: String) : RuntimeException(mensagem)