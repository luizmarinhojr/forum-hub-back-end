package com.luizmarinho.forumhub.domain.topico.validacoes.cadastro;

import com.luizmarinho.forumhub.domain.ValidacaoException;
import com.luizmarinho.forumhub.domain.ValidacaoExceptionNotFound;
import com.luizmarinho.forumhub.domain.topico.TopicoDTOEntrada;
import com.luizmarinho.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorIdAutor implements ValidadorCadastro {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validar(TopicoDTOEntrada topicoEntrada) {
        if (topicoEntrada.autorId() == null || !repository.existsById(topicoEntrada.autorId())) {
            throw new ValidacaoExceptionNotFound("Id do autor informado não existe");
        }
    }
}
