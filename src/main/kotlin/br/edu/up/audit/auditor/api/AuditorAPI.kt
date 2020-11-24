package br.edu.up.audit.auditor.api

import br.edu.up.audit.auditor.api.dto.AuditorDTO
import br.edu.up.audit.auditor.facade.AuditorFacade
import br.edu.up.audit.autenticacao.facade.AutenticacaoFacade
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest/{cnpj}/auditores")
class AuditorAPI(private val auditorFacade: AuditorFacade,
                 private val autenticacaoFacade: AutenticacaoFacade) {

    @PostMapping
    @Secured("ROLE_ADMIN")
    fun cadastrar(@RequestBody auditor: AuditorDTO): AuditorDTO =
            auditorFacade.cadastrar(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    entrada = auditor
            )

    @PatchMapping("{cpf}")
    @Secured("ROLE_ADMIN")
    fun editar(@RequestBody auditor: AuditorDTO): AuditorDTO =
            auditorFacade.editar(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    entrada = auditor
            )

    @GetMapping
    @Secured("ROLE_ADMIN")
    fun listar(): List<AuditorDTO> =
            auditorFacade.listar(cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj)

    @GetMapping("{cpf}")
    @Secured("ROLE_ADMIN")
    fun obter(@PathVariable("cpf") cpf: String): AuditorDTO =
            auditorFacade.obter(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    cpf = cpf
            )

    @DeleteMapping("{cpf}")
    @Secured("ROLE_ADMIN")
    fun remover(@PathVariable("cpf") cpf: String) =
            auditorFacade.remover(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    cpf = cpf
            )
}