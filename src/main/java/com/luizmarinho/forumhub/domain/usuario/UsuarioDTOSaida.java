package com.luizmarinho.forumhub.domain.usuario;

import com.luizmarinho.forumhub.domain.perfil.Perfil;

import java.util.Set;
import java.util.stream.Collectors;

public record UsuarioDTOSaida(
        Long id,
        String nome,
        String email,
        Set<String> perfis
) {
    public UsuarioDTOSaida(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getPerfis().stream().map(Perfil::getNome).collect(Collectors.toSet()));
    }
}
