package com.luizmarinho.forumhub.domain.curso;


import jakarta.validation.constraints.NotEmpty;

public record CursoDTOEntrada(
        @NotEmpty
        String nome,

        @NotEmpty
        String categoria) {
}
