package br.edu.up.audit.autenticacao.handler

import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class AutenticacaoLogoutHandler : SimpleUrlLogoutSuccessHandler() {

    override fun onLogoutSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        response?.sendError(HttpServletResponse.SC_OK, "Sessão encerrada com sucesso")
    }
}