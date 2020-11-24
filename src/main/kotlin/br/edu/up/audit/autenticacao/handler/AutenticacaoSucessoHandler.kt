package br.edu.up.audit.autenticacao.handler

import br.edu.up.audit.autenticacao.business.objects.AutenticacaoDetalhe
import br.edu.up.audit.autenticacao.converter.toDTO
import br.edu.up.audit.autenticacao.converter.toJSON
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class AutenticacaoSucessoHandler : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        clearAuthenticationAttributes(request)
                .also {
                    response?.run {
                        contentType = MediaType.APPLICATION_JSON_VALUE
                        characterEncoding = StandardCharsets.UTF_8.name()
                        status = HttpServletResponse.SC_OK
                        writer.print((authentication?.principal as AutenticacaoDetalhe).toDTO().toJSON())
                        writer.flush()
                    }
                }
    }
}