package com.luizmarinho.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luizmarinho.forumhub.domain.curso.Curso;
import com.luizmarinho.forumhub.domain.usuario.Usuario;

public record TopicoDTOSaida(
        Long id,
        String titulo,
        String mensagem,
        @JsonProperty(value = "autor_id") Usuario autorId,
        @JsonProperty(value = "curso_id") Curso cursoId
) {

    public TopicoDTOSaida (Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getAutor(), topico.getCurso());
    }

}
