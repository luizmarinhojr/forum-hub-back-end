package com.luizmarinho.forumhub.infra.exception;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.exception.ValidacaoExceptionNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(ValidacaoExceptionNotFound.class)
    public ResponseEntity tratarErro404(ValidacaoExceptionNotFound ex) {
        return ResponseEntity.notFound().header("Error", ex.getMessage()).build();
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroValidacao(ValidacaoException ex) {
        return ResponseEntity.badRequest().body(new ValidationExceptionDTO(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErroValidacaoCamposObrigatorios(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ValidationMethodArgumentDTO::new));
    }

    private record ValidationExceptionDTO(String mensagem) {
    }

    private record ValidationMethodArgumentDTO(String campo, String mensagem) {
        public ValidationMethodArgumentDTO(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
