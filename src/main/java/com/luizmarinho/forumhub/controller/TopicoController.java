package com.luizmarinho.forumhub.controller;

import com.luizmarinho.forumhub.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService service;


    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid TopicoDTOEntrada topicoDados, UriComponentsBuilder uriBuilder) {
        var topico = service.cadastrar(topicoDados);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.id()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }

    @GetMapping
    public ResponseEntity listar(@PageableDefault(size = 10, sort = {"data_criacao"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        var pagina = service.listar(paginacao);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var topico = service.detalhar(id);
        return ResponseEntity.ok(topico);
    }

    public ResponseEntity atualizar() {
        return ResponseEntity.ok("temporary");
    }
}
