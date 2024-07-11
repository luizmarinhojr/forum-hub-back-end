package com.luizmarinho.forumhub.domain.perfil;

public record PerfilDTOSaida(Long id, String nome) {

    public PerfilDTOSaida(Perfil perfil) {
        this(perfil.getId(), perfil.getNome());
    }
}
