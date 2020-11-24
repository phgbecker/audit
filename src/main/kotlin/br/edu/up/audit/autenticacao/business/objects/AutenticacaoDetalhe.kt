package br.edu.up.audit.autenticacao.business.objects

import br.edu.up.audit.auditor.model.AuditorModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AutenticacaoDetalhe(val auditor: AuditorModel) : UserDetails {

    override fun getUsername(): String =
            auditor.email

    override fun getPassword(): String =
            auditor.senha

    override fun isAccountNonExpired(): Boolean =
            true

    override fun isAccountNonLocked(): Boolean =
            true

    override fun isCredentialsNonExpired(): Boolean =
            true

    override fun isEnabled(): Boolean =
            true

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
            when {
                auditor.isAdministrador ->
                    mutableListOf(SimpleGrantedAuthority("ROLE_ADMIN"))

                else ->
                    mutableListOf(SimpleGrantedAuthority("ROLE_AUDITOR"))
            }
}