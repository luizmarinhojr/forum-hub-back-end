package com.luizmarinho.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TopicoDTOEntrada(
        @NotEmpty
        String titulo,

        @NotEmpty
        String mensagem,

        @JsonProperty(value = "curso_id")
        @NotNull
        Long cursoId
) {
}
