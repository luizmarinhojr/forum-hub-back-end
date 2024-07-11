package com.luizmarinho.forumhub.domain.usuario.validacoes.cadastro;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.usuario.UsuarioDTOEntrada;
import org.springframework.stereotype.Component;

@Component
public class ValidadorSenha implements ValidadorUsuarioCadastro{
    @Override
    public void validar(UsuarioDTOEntrada usuarioEntrada) {
        if (usuarioEntrada.senha().length() <= 8) {
            throw new ValidacaoException("A senha precisa possuir mais de 8 caracteres");
        }
    }
}
