package com.luizmarinho.forumhub.domain.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UsuarioDTOPatch(
        @JsonProperty(value = "perfil_id")
        @NotNull
        Set<Long> perfilId
) {
}
