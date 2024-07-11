package com.luizmarinho.forumhub.domain.usuario.validacoes.atualizacao;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.usuario.UsuarioDTOAtualizacao;
import org.springframework.stereotype.Component;

@Component
public class ValidadorSenhaAtualizacao implements ValidadorUsuarioAtualizacao{
    @Override
    public void validar(UsuarioDTOAtualizacao usuarioAtualizacao) {
        if (usuarioAtualizacao.senha() != null) {
            if (usuarioAtualizacao.senha().length() <= 8) {
                throw new ValidacaoException("A senha precisa possuir mais de 8 caracteres");
            }
        }
    }
}
