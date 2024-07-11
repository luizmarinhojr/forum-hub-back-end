package com.luizmarinho.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luizmarinho.forumhub.domain.resposta.RespostaDTOSaida;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record TopicoDTODetalharSaida(
        Long id,
        String titulo,
        String mensagem,
        @JsonProperty(value = "data_criacao") LocalDateTime dataCriacao,
        @JsonProperty(value = "nome_autor") String nomeAutor,
        StatusEnum status,
        String curso,
        @JsonProperty(value = "respostas", defaultValue = "Ainda não há respostas") Set<RespostaDTOSaida> respostas,
        @JsonProperty(value = "solucao_resposta_id", defaultValue = "Ainda sem solução especificada") Long resposta_id
) {

    public TopicoDTODetalharSaida (Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getAutor().getNome(),
                topico.getStatus(),
                topico.getCurso().getNome(),
                topico.getRespostas().stream().map(RespostaDTOSaida::new).collect(Collectors.toSet()),
                topico.getSolucaoResposta() == null ? null : topico.getSolucaoResposta().getId());
    }

}
