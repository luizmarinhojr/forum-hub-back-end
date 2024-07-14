package com.luizmarinho.forumhub.domain.perfil;

import com.luizmarinho.forumhub.domain.perfil.validacao.ValidadorPerfilDuplicado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private ValidadorPerfilDuplicado validadorDuplicacao;

    public Perfil cadastrar(PerfilDTOEntrada perfilEntrada) {
        validadorDuplicacao.validar(perfilEntrada);
        var perfil = new Perfil(perfilEntrada);
        perfilRepository.save(perfil);
        return perfil;
    }
}
