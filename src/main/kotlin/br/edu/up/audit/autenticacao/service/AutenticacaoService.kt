package br.edu.up.audit.autenticacao.service

import br.edu.up.audit.auditor.repository.AuditorRepository
import br.edu.up.audit.autenticacao.business.objects.AutenticacaoDetalhe
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AutenticacaoService(private val repositorio: AuditorRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
            repositorio.findByEmailAndIsAtivoIsTrue(email = username)
                    ?.run {
                        AutenticacaoDetalhe(auditor = this)
                    }
                    ?: throw UsernameNotFoundException(username)
}