package br.edu.up.audit.auditor.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class AuditorExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(AuditorNaoEncontradoException::class)
    protected fun handleNaoEncontrado(exception: RuntimeException, request: WebRequest): ResponseEntity<Any> =
            handleExceptionInternal(
                    exception,
                    mapOf("erro" to exception.localizedMessage),
                    HttpHeaders(),
                    HttpStatus.NOT_FOUND,
                    request
            )

    @ExceptionHandler(AuditorCadastradoException::class)
    protected fun handleCadastrado(exception: RuntimeException, request: WebRequest): ResponseEntity<Any> =
            handleExceptionInternal(
                    exception,
                    mapOf("erro" to exception.localizedMessage),
                    HttpHeaders(),
                    HttpStatus.CONFLICT,
                    request
            )
}