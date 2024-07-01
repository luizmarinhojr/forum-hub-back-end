package com.luizmarinho.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TopicoDTOSaida(
        Long id,
        String titulo,
        String mensagem,
        @JsonProperty(value = "data_criacao") LocalDateTime dataCriacao,
        @JsonProperty(value = "autor_id") Long autorId,
        @JsonProperty(value = "curso_id") Long cursoId
) {

    public TopicoDTOSaida (Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao() , topico.getAutor().getId(), topico.getCurso().getId());
    }

}
