package com.luizmarinho.forumhub.domain.resposta;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record RespostaDTOSaida(
        Long id,
        String mensagem,
        String solucao,
        String autor,
        @JsonProperty("data_criacao") LocalDateTime dataCriacao,
        @JsonProperty("topico_id") Long topicoId
) {
    public RespostaDTOSaida(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(), resposta.getSolucao(), resposta.getAutor().getNome(), resposta.getDataCriacao(), resposta.getTopico().getId());
    }
}
