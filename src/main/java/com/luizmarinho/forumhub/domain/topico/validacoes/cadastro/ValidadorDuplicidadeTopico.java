package com.luizmarinho.forumhub.domain.topico.validacoes.cadastro;

import com.luizmarinho.forumhub.domain.ValidacaoException;
import com.luizmarinho.forumhub.domain.topico.TopicoDTOEntrada;
import com.luizmarinho.forumhub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDuplicidadeTopico {

    @Autowired
    private TopicoRepository repository;

    public void validar(TopicoDTOEntrada topicoEntrada) {
        var topicoTitulo = repository.buscarPorTituloIgual(topicoEntrada.titulo());
        var topicoMensagem = repository.buscarPorMensagemIgual(topicoEntrada.mensagem());

        if (topicoTitulo.isPresent()) {
            throw new ValidacaoException("Já existe um tópico com esse título");
        }

        if (topicoMensagem.isPresent()) {
            throw new ValidacaoException("Já existe um tópico com essa mensagem");
        }
    }
}
