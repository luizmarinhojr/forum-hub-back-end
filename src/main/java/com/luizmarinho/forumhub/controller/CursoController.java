package com.luizmarinho.forumhub.controller;

import com.luizmarinho.forumhub.domain.curso.Curso;
import com.luizmarinho.forumhub.domain.curso.CursoDTOEntrada;
import com.luizmarinho.forumhub.domain.curso.CursoDTOSaida;
import com.luizmarinho.forumhub.domain.curso.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CursoDTOEntrada cursoEntrada, UriComponentsBuilder uriBuilder) {
        Curso curso = service.cadastrar(cursoEntrada);

        var uri = uriBuilder.path("/curso/{id}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).body(new CursoDTOSaida(curso));
    }
}
