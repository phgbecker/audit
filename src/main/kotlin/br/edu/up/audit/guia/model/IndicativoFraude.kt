package br.edu.up.audit.guia.model

enum class IndicativoFraude(val valor: Int? = null,
                            val score: Float? = null) {
    SIM(valor = 0),
    NAO(valor = 1),
    INCONCLUSIVO(score = 0.5F)
}