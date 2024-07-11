package com.luizmarinho.forumhub.domain.perfil;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public Perfil cadastrar(PerfilDTOEntrada perfilEntrada, Authentication authentication) {
        var usuario = (Usuario) authentication.getPrincipal();
        for (Perfil p : usuario.getPerfis()) {
            if (p.getNome().equals("ROLE_ADM")) {
                var perfil = new Perfil(perfilEntrada);
                perfilRepository.save(perfil);
                return perfil;
            }
        }
        throw new ValidacaoException("O usuário não possui autorização para criar novos perfis");
    }
}
