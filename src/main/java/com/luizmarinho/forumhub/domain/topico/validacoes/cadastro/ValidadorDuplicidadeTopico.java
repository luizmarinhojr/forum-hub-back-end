package com.luizmarinho.forumhub.domain.topico.validacoes.cadastro;

import com.luizmarinho.forumhub.domain.ValidacaoException;
import com.luizmarinho.forumhub.domain.topico.TopicoDTOEntrada;
import com.luizmarinho.forumhub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDuplicidadeTopico implements ValidadorInterface{

    @Autowired
    private TopicoRepository repository;

    @Override
    public void validar(TopicoDTOEntrada topicoEntrada) {
        if (repository.buscarPorTituloIgual(topicoEntrada.titulo()).isPresent()) {
            throw new ValidacaoException("Já existe um tópico com esse título");
        }

        if (repository.buscarPorMensagemIgual(topicoEntrada.mensagem()).isPresent()) {
            throw new ValidacaoException("Já existe um tópico com essa mensagem");
        }
    }
}
