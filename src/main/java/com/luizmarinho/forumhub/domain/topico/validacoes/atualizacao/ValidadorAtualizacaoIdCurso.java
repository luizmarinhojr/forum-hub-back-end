package com.luizmarinho.forumhub.domain.topico.validacoes.atualizacao;

import com.luizmarinho.forumhub.domain.ValidacaoException;
import com.luizmarinho.forumhub.domain.ValidacaoExceptionNotFound;
import com.luizmarinho.forumhub.domain.curso.CursoRepository;
import com.luizmarinho.forumhub.domain.topico.TopicoDTOAtualizacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAtualizacaoIdCurso implements ValidadorAtualizacao{

    @Autowired
    private CursoRepository repository;

    @Override
    public void validar(TopicoDTOAtualizacao topicoAtualizacao) {
        if (topicoAtualizacao.cursoId() != null && !repository.existsById(topicoAtualizacao.cursoId())) {
            throw new ValidacaoExceptionNotFound("Id do curso informado não existe");
        }
    }
}
