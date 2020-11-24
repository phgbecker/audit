package br.edu.up.audit.motor.api

import br.edu.up.audit.autenticacao.facade.AutenticacaoFacade
import br.edu.up.audit.motor.api.dto.MotorDTO
import br.edu.up.audit.motor.facade.MotorFacade
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/rest/{cnpj}/motores")
class MotorAPI(private val motorFacade: MotorFacade,
               private val autenticacaoFacade: AutenticacaoFacade) {

    @GetMapping
    @Secured("ROLE_ADMIN")
    fun listar(): List<MotorDTO> =
            motorFacade.listar(cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj)

    @PostMapping("treinamento")
    @Secured("ROLE_ADMIN")
    fun treinar(@RequestParam("arquivo") arquivo: MultipartFile) =
            motorFacade.treinar(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    arquivo = arquivo.bytes
            )
}