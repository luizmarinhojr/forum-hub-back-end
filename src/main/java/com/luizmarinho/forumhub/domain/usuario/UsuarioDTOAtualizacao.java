package com.luizmarinho.forumhub.domain.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record UsuarioDTOAtualizacao(
        String nome,
        String email,
        String senha,
        @JsonProperty(value = "perfis_id") Set<Long> perfisId) {
}
