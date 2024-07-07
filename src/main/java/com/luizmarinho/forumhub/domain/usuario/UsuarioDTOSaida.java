package com.luizmarinho.forumhub.domain.usuario;

public record UsuarioDTOSaida(
        Long id,
        String nome,
        String email
) {
    public UsuarioDTOSaida(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
