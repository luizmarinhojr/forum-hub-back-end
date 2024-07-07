package com.luizmarinho.forumhub.domain.topico.validacoes.cadastro;

import com.luizmarinho.forumhub.domain.topico.TopicoDTOEntrada;

public interface ValidadorCadastro {

    void validar(TopicoDTOEntrada topicoEntrada);
}
