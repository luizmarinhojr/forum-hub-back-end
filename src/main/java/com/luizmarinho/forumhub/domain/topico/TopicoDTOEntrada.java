package com.luizmarinho.forumhub.domain.topico;

import com.luizmarinho.forumhub.domain.curso.Curso;
import com.luizmarinho.forumhub.domain.usuario.Usuario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TopicoDTOEntrada(
        @NotEmpty
        String titulo,

        @NotEmpty
        String mensagem,

        @NotNull
        Usuario autor,

        @NotNull
        Curso curso
) {
}
