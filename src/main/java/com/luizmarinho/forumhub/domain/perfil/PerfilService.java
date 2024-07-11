package com.luizmarinho.forumhub.domain.perfil;

import com.luizmarinho.forumhub.domain.exception.ValidacaoExceptionAuthorization;
import com.luizmarinho.forumhub.domain.perfil.validacao.ValidadorPerfilDuplicado;
import com.luizmarinho.forumhub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private ValidadorPerfilDuplicado validadorDuplicacao;

    public Perfil cadastrar(PerfilDTOEntrada perfilEntrada, Authentication authentication) {
        var usuario = (Usuario) authentication.getPrincipal();
        for (Perfil p : usuario.getPerfis()) {
            if (p.getNome().equals("ROLE_ADM")) {
                validadorDuplicacao.validar(perfilEntrada);
                var perfil = new Perfil(perfilEntrada);
                perfilRepository.save(perfil);
                return perfil;
            }
        }
        throw new ValidacaoExceptionAuthorization("O usuário não possui autorização para criar novos perfis");
    }
}
