package br.edu.up.audit.guia.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GuiaExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(GuiaNaoEncontradaException::class)
    protected fun handleNaoEncontrado(exception: RuntimeException, request: WebRequest): ResponseEntity<Any> =
            handleExceptionInternal(
                    exception,
                    mapOf("erro" to exception.localizedMessage),
                    HttpHeaders(),
                    HttpStatus.NOT_FOUND,
                    request
            )

    @ExceptionHandler(GuiaCadastradaException::class)
    protected fun handleCadastrada(exception: RuntimeException, request: WebRequest): ResponseEntity<Any> =
            handleExceptionInternal(
                    exception,
                    mapOf("erro" to exception.localizedMessage),
                    HttpHeaders(),
                    HttpStatus.CONFLICT,
                    request
            )

    @ExceptionHandler(GuiaInvalidaException::class)
    protected fun handleInvalida(exception: RuntimeException, request: WebRequest): ResponseEntity<Any> =
            handleExceptionInternal(
                    exception,
                    mapOf("erro" to exception.localizedMessage),
                    HttpHeaders(),
                    HttpStatus.FAILED_DEPENDENCY,
                    request
            )

    @ExceptionHandler(GuiaNaoProcessadaException::class)
    protected fun handleNaoProcessada(exception: RuntimeException, request: WebRequest): ResponseEntity<Any> =
            handleExceptionInternal(
                    exception,
                    mapOf("erro" to exception.localizedMessage),
                    HttpHeaders(),
                    HttpStatus.EXPECTATION_FAILED,
                    request
            )
}