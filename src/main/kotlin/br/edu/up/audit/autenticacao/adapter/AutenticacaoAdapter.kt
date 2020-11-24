package br.edu.up.audit.autenticacao.adapter

import br.edu.up.audit.autenticacao.entrypoint.AutenticacaoEntrypoint
import br.edu.up.audit.autenticacao.handler.AutenticacaoFalhaHandler
import br.edu.up.audit.autenticacao.handler.AutenticacaoLogoutHandler
import br.edu.up.audit.autenticacao.handler.AutenticacaoSucessoHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class AutenticacaoAdapter(
        private val entryPoint: AutenticacaoEntrypoint,
        private val sucessoHandler: AutenticacaoSucessoHandler,
        private val falhaHandler: AutenticacaoFalhaHandler,
        private val logoutHandler: AutenticacaoLogoutHandler
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.run {
            authorizeRequests()
                    .antMatchers("/rest/**")
                        .authenticated()
                    .and()
                        .csrf()
                        .disable()
                    .exceptionHandling()
                        .authenticationEntryPoint(entryPoint)
                    .and()
                    .formLogin()
                        .successHandler(sucessoHandler)
                        .failureHandler(falhaHandler)
                    .and()
                    .logout()
                        .logoutSuccessHandler(logoutHandler)
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder =
            BCryptPasswordEncoder()
}