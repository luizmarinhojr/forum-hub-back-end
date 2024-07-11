package com.luizmarinho.forumhub.domain.exception;

public class ValidacaoExceptionAuthorization extends RuntimeException{

    public ValidacaoExceptionAuthorization(String mensagem) {
        super(mensagem);
    }
}
