package com.luizmarinho.forumhub.domain.topico.validacoes.cadastro;

import com.luizmarinho.forumhub.domain.ValidacaoException;
import com.luizmarinho.forumhub.domain.curso.CursoRepository;
import com.luizmarinho.forumhub.domain.topico.TopicoDTOEntrada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorIdCurso implements ValidadorInterface{

    @Autowired
    private CursoRepository repository;

    @Override
    public void validar(TopicoDTOEntrada topicoEntrada) {
        if (topicoEntrada.cursoId() == null || !repository.existsById(topicoEntrada.cursoId())) {
            throw new ValidacaoException("Id do curso informado não existe");
        }
    }
}
