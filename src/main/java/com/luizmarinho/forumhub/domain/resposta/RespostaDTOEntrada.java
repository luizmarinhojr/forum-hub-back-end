package com.luizmarinho.forumhub.domain.resposta;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RespostaDTOEntrada(
        @NotEmpty
        String mensagem,

        Long topicoId,

        @JsonProperty("usuario_id")
        @NotNull
        Long usuarioId,

        @NotEmpty
        String solucao) {
}
