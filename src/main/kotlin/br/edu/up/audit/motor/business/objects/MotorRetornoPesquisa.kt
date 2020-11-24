package br.edu.up.audit.motor.business.objects

import br.edu.up.audit.motor.model.MotorModel

sealed class MotorRetornoPesquisa {
    class Encontrado(val motor: MotorModel) : MotorRetornoPesquisa()
    class NaoEncontrado(val mensagem: String) : MotorRetornoPesquisa()
}