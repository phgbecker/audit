package br.edu.up.audit.guia.api

import br.edu.up.audit.autenticacao.facade.AutenticacaoFacade
import br.edu.up.audit.guia.api.dto.GuiaDTO
import br.edu.up.audit.guia.facade.GuiaFacade
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest/{cnpj}/guias")
class GuiaAPI(private val guiaFacade: GuiaFacade,
              private val autenticacaoFacade: AutenticacaoFacade) {

    @PostMapping
    fun cadastrar(@RequestBody guia: GuiaDTO): GuiaDTO =
            guiaFacade.cadastrar(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    entrada = guia
            )

    @GetMapping
    fun listar(): List<GuiaDTO> =
            guiaFacade.listar(cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj)

    @GetMapping("{codigo}")
    fun obter(@PathVariable("codigo") codigo: Long): GuiaDTO =
            guiaFacade.obter(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    codigo = codigo
            )

    @DeleteMapping("{codigo}")
    fun remover(@PathVariable("codigo") codigo: Long) =
            guiaFacade.remover(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    codigo = codigo
            )
}