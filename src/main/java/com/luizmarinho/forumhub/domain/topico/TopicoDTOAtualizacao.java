package com.luizmarinho.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TopicoDTOAtualizacao(
        String titulo,

        String mensagem,

        Boolean status,

        @JsonProperty(value = "curso_id")
        Long cursoId
) {
}
