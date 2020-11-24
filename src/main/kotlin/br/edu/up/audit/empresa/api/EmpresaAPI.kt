package br.edu.up.audit.empresa.api

import br.edu.up.audit.autenticacao.facade.AutenticacaoFacade
import br.edu.up.audit.empresa.api.dto.EmpresaDTO
import br.edu.up.audit.empresa.facade.EmpresaFacade
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest/{cnpj}/empresa")
class EmpresaAPI(private val empresaFacade: EmpresaFacade,
                 private val autenticacaoFacade: AutenticacaoFacade) {

    @PatchMapping
    @Secured("ROLE_ADMIN")
    fun editar(@RequestBody entrada: EmpresaDTO) =
            empresaFacade.editar(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    entrada = entrada
            )

    @GetMapping
    fun obter(): EmpresaDTO =
            empresaFacade.obter(cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj)
}