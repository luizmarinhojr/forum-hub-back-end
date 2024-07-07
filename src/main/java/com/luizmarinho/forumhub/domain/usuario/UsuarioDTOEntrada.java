package com.luizmarinho.forumhub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UsuarioDTOEntrada(

        @NotEmpty
        String nome,

        @Email
        @NotEmpty
        String email,

        @NotEmpty
        String senha
) {
}
