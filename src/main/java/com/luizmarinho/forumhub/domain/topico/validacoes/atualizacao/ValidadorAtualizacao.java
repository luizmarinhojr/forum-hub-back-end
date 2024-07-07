package com.luizmarinho.forumhub.domain.topico.validacoes.atualizacao;

import com.luizmarinho.forumhub.domain.topico.TopicoDTOAtualizacao;

public interface ValidadorAtualizacao {

    void validar(TopicoDTOAtualizacao topicoAtualizacao);
}
