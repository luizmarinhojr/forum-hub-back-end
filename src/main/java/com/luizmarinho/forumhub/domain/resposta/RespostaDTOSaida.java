package com.luizmarinho.forumhub.domain.resposta;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record RespostaDTOSaida(
        Long id,
        String mensagem,
        @JsonProperty("data_criacao") LocalDateTime dataCriacao,
        @JsonProperty("topico_id") Long topicoId,
        @JsonProperty("usuario_id") Long usuarioId,
        String solucao
) {
    public RespostaDTOSaida(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(), resposta.getDataCriacao(), resposta.getTopico().getId(), resposta.getAutor().getId(), resposta.getSolucao());
    }
}
