package br.edu.up.audit.motor.business.objects

sealed class MotorRetornoTreinamento {
    class Sucesso(val nome: String,
                  val endpoint: String,
                  val metricas: List<MotorMetricaModelo>) : MotorRetornoTreinamento()

    class Falha(val mensagem: String) : MotorRetornoTreinamento()
}