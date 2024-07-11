package com.luizmarinho.forumhub.domain.resposta;

import jakarta.validation.constraints.NotEmpty;

public record RespostaDTOEntrada(
        @NotEmpty
        String mensagem,

        Long topicoId,

        @NotEmpty
        String solucao) {
}
