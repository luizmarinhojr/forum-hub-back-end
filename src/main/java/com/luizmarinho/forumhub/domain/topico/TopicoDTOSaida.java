package com.luizmarinho.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TopicoDTOSaida(
        Long id,
        String titulo,
        String mensagem,
        @JsonProperty(value = "data_criacao") LocalDateTime dataCriacao,
        @JsonProperty(value = "nome_autor") String nomeAutor,
        StatusEnum status,
        String curso,
        @JsonProperty(value = "contagem_respostas", defaultValue = "Ainda não há respostas") Integer contagemRespostas,
        @JsonProperty(value = "solucao_resposta_id", defaultValue = "Ainda sem solução especificada") Long resposta_id
) {

    public TopicoDTOSaida (Topico topico) {
        this(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensagem(),
            topico.getDataCriacao(),
            topico.getAutor().getNome(),
            topico.getStatus(),
            topico.getCurso().getNome(),
            topico.getRespostas() == null ? 0 : topico.getRespostas().size(),
            topico.getSolucaoResposta() == null ? null : topico.getSolucaoResposta().getId());
    }

}
