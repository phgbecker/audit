package br.edu.up.audit.empresa.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class EmpresaExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EmpresaNaoEncontradaException::class)
    protected fun handle(exception: RuntimeException, request: WebRequest): ResponseEntity<Any> =
            handleExceptionInternal(
                    exception,
                    mapOf("erro" to exception.localizedMessage),
                    HttpHeaders(),
                    HttpStatus.NOT_FOUND,
                    request
            )
}