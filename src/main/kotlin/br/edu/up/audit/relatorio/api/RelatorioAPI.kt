package br.edu.up.audit.relatorio.api

import br.edu.up.audit.autenticacao.facade.AutenticacaoFacade
import br.edu.up.audit.relatorio.api.dto.RelatorioDTO
import br.edu.up.audit.relatorio.api.dto.VisaoGeralDTO
import br.edu.up.audit.relatorio.facade.RelatorioFacade
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest/{cnpj}/relatorios")
class RelatorioAPI(private val relatorioFacade: RelatorioFacade,
                   private val autenticacaoFacade: AutenticacaoFacade) {

    @GetMapping("geral")
    fun geral(@RequestParam("dataInicial") dataInicial: String?,
              @RequestParam("dataFinal") dataFinal: String?): VisaoGeralDTO =
            relatorioFacade.geral(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    dataInicial = dataInicial,
                    dataFinal = dataFinal
            )

    @GetMapping("idade")
    fun idade(@RequestParam("dataInicial") dataInicial: String?,
              @RequestParam("dataFinal") dataFinal: String?): RelatorioDTO =
            relatorioFacade.guiasPorIdade(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    dataInicial = dataInicial,
                    dataFinal = dataFinal
            )

    @GetMapping("especialidade")
    fun especialidade(@RequestParam("dataInicial") dataInicial: String?,
                      @RequestParam("dataFinal") dataFinal: String?): RelatorioDTO =
            relatorioFacade.guiasPorEspecialidade(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    dataInicial = dataInicial,
                    dataFinal = dataFinal
            )

    @GetMapping("prestador")
    fun prestador(@RequestParam("dataInicial") dataInicial: String?,
                  @RequestParam("dataFinal") dataFinal: String?): RelatorioDTO =
            relatorioFacade.guiasPorPrestador(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    dataInicial = dataInicial,
                    dataFinal = dataFinal
            )

    @GetMapping("tempo")
    fun indicativoTempo(@RequestParam("dataInicial") dataInicial: String?,
                        @RequestParam("dataFinal") dataFinal: String?): RelatorioDTO =
            relatorioFacade.guiasPorTempo(
                    cnpj = autenticacaoFacade.obterEmpresaSessao().cnpj,
                    dataInicial = dataInicial,
                    dataFinal = dataFinal
            )
}