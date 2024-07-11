package com.luizmarinho.forumhub.domain.usuario.validacoes.cadastro;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.usuario.UsuarioDTOEntrada;
import com.luizmarinho.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDuplicacaoEmail implements ValidadorUsuarioCadastro{

    @Autowired
    UsuarioRepository repository;

    @Override
    public void validar(UsuarioDTOEntrada usuarioEntrada) {
        var usuario = repository.buscarUsuarioPorEmail(usuarioEntrada.email());
        if (usuario.isPresent()) {
            throw new ValidacaoException("Já existe um usuário cadastrado com o e-mail informado");
        }
    }
}
