package com.luizmarinho.forumhub.domain.resposta.validacoes.cadastro;

import com.luizmarinho.forumhub.domain.ValidacaoException;
import com.luizmarinho.forumhub.domain.ValidacaoExceptionNotFound;
import com.luizmarinho.forumhub.domain.resposta.RespostaDTOEntrada;
import com.luizmarinho.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorRespostaIdAutor implements ValidadorRespostaCadastro{

    @Autowired
    UsuarioRepository repository;

    @Override
    public void validar(RespostaDTOEntrada respostaEntrada) {
        if (!repository.existsById(respostaEntrada.usuarioId())) {
            throw new ValidacaoExceptionNotFound("Id do autor informado não existe");
        }
    }
}
