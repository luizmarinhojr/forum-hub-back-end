package com.luizmarinho.forumhub.domain.curso;

import com.luizmarinho.forumhub.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    @Autowired
    private CursoRepository repository;

    public Curso cadastrar(CursoDTOEntrada cursoEntrada) {
        if (repository.buscarPorNomeIgual(cursoEntrada.nome()).isPresent()) {
            throw new ValidacaoException("Já existe um curso cadastrado com esse nome");
        }
        Curso curso = new Curso(cursoEntrada);
        repository.save(curso);

        return curso;
    }
}
