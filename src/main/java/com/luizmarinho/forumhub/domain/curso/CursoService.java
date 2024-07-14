package com.luizmarinho.forumhub.domain.curso;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<CursoDTOSaida> listar() {
        var cursos = repository.findAll();
        return cursos.stream().map(CursoDTOSaida::new).toList();
    }
}
