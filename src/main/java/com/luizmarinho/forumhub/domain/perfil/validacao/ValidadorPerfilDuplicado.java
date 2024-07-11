package com.luizmarinho.forumhub.domain.perfil.validacao;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.perfil.PerfilDTOEntrada;
import com.luizmarinho.forumhub.domain.perfil.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPerfilDuplicado {

    @Autowired
    private PerfilRepository repository;

    public void validar(PerfilDTOEntrada perfilEntrada) {
        var perfil = repository.findByNome(perfilEntrada.nome());
        if (perfil.isPresent()) {
            throw new ValidacaoException("Já existe um perfil com o nome informado");
        }
    }
}
