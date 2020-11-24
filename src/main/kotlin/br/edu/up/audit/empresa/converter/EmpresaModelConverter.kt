package br.edu.up.audit.empresa.converter

import br.edu.up.audit.empresa.api.dto.EmpresaDTO
import br.edu.up.audit.empresa.model.EmpresaModel

fun EmpresaModel.toDTO(): EmpresaDTO =
        EmpresaDTO(
                cnpj = cnpj,
                razaoSocial = razaoSocial,
                nomeFantasia = nomeFantasia,
                cep = cep,
                endereco = endereco,
                numero = numero,
                bairro = bairro,
                complemento = complemento,
                cidade = cidade,
                estado = estado,
                telefone = telefone,
                email = email,
                registroANS = registroANS
        )