package com.luizmarinho.forumhub.controller;

import com.luizmarinho.forumhub.domain.topico.Topico;
import com.luizmarinho.forumhub.domain.topico.TopicoDTOEntrada;
import com.luizmarinho.forumhub.domain.topico.TopicoDTOSaida;
import com.luizmarinho.forumhub.domain.topico.TopicoRepository;
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
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid TopicoDTOEntrada topicoData, UriComponentsBuilder uriBuilder) {
        var topico = new Topico(topicoData);
        repository.save(topico);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDTOSaida(topico));
    }
}
