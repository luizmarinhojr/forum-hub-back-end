package com.luizmarinho.forumhub.domain.topico.validacoes.atualizacao;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.topico.TopicoDTOAtualizacao;
import com.luizmarinho.forumhub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAtualizacaoDuplicidadeTopico implements ValidadorAtualizacao{

    @Autowired
    private TopicoRepository repository;

    @Override
    public void validar(TopicoDTOAtualizacao topicoAtualizacao) {
        if (repository.buscarPorTituloIgual(topicoAtualizacao.titulo()).isPresent()) {
            throw new ValidacaoException("Já existe um tópico com esse título");
        }

        if (repository.buscarPorMensagemIgual(topicoAtualizacao.mensagem()).isPresent()) {
            throw new ValidacaoException("Já existe um tópico com essa mensagem");
        }
    }
}
