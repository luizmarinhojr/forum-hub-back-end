package com.luizmarinho.forumhub.domain.curso;

public record CursoDTOSaida(Long id, String nome, String categoria) {

    public CursoDTOSaida(Curso curso) {
        this(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}
