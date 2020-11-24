package br.edu.up.audit.autenticacao.facade

import br.edu.up.audit.auditor.model.AuditorModel
import br.edu.up.audit.autenticacao.business.objects.AutenticacaoDetalhe
import br.edu.up.audit.autenticacao.exception.SessaoInvalidaException
import br.edu.up.audit.empresa.model.EmpresaModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AutenticacaoFacade {

    fun obterAuditorSessao(): AuditorModel =
            obterAutenticacao()
                    ?.run {
                        auditor
                    }
                    ?: throw SessaoInvalidaException("Não foi possível obter o auditor da sessão")

    fun obterEmpresaSessao(): EmpresaModel =
            obterAutenticacao()
                    ?.run {
                        auditor.empresa
                    }
                    ?: throw SessaoInvalidaException("Não foi possível obter a empresa da sessão")

    fun obterPerfil(): MutableCollection<out GrantedAuthority> =
            obterAutenticacao()
                    ?.run {
                        authorities
                    }
                    ?: throw SessaoInvalidaException("Não foi possível obter o perfil da sessão")

    private fun obterAutenticacao(): AutenticacaoDetalhe? =
            SecurityContextHolder.getContext()
                    .authentication
                    .principal as AutenticacaoDetalhe
}