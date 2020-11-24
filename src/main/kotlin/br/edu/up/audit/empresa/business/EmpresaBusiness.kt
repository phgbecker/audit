package br.edu.up.audit.empresa.business

import br.edu.up.audit.empresa.business.objects.EmpresaRetornoPesquisa
import br.edu.up.audit.empresa.business.objects.EmpresaRetornoPesquisa.Encontrado
import br.edu.up.audit.empresa.business.objects.EmpresaRetornoPesquisa.NaoEncontrado
import br.edu.up.audit.empresa.model.EmpresaModel
import br.edu.up.audit.empresa.repository.EmpresaRepository
import org.springframework.stereotype.Service

@Service
class EmpresaBusiness(private val repositorio: EmpresaRepository) {

    fun editar(empresa: EmpresaModel): EmpresaModel =
            repositorio.save(empresa)

    fun obter(cnpj: String): EmpresaRetornoPesquisa =
            repositorio.findByCnpj(cnpj)
                    ?.run {
                        Encontrado(empresa = this)
                    }
                    ?: NaoEncontrado(mensagem = "Não foi possível obter a empresa a partir do CNPJ $cnpj")
}