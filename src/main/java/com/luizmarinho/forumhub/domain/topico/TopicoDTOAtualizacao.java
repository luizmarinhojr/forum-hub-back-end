package com.luizmarinho.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TopicoDTOAtualizacao(
        String titulo,

        String mensagem,

        StatusEnum status,

        @JsonProperty(value = "curso_id")
        Long cursoId,

        @JsonProperty(value = "solucao_resposta_id", defaultValue = "Ainda sem solução")
        Long solucaoId
) {
}
