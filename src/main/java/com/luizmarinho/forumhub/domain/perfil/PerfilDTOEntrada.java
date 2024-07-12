package com.luizmarinho.forumhub.domain.perfil;

import jakarta.validation.constraints.NotEmpty;

public record PerfilDTOEntrada(@NotEmpty String nome) {
}
