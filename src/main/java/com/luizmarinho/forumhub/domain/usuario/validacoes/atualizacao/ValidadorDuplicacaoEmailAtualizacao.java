package com.luizmarinho.forumhub.domain.usuario.validacoes.atualizacao;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.usuario.UsuarioDTOAtualizacao;
import com.luizmarinho.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDuplicacaoEmailAtualizacao implements ValidadorUsuarioAtualizacao{

    @Autowired
    UsuarioRepository repository;

    @Override
    public void validar(UsuarioDTOAtualizacao usuarioAtualizacao) {
        if (usuarioAtualizacao != null) {
            var usuario = repository.buscarUsuarioPorEmail(usuarioAtualizacao.email());
            if (usuario.isPresent()) {
                throw new ValidacaoException("Já existe um usuário cadastrado com o e-mail informado");
            }
        }
    }
}
