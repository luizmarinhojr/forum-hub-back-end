package com.luizmarinho.forumhub.controller;

import com.luizmarinho.forumhub.domain.topico.*;
import com.luizmarinho.forumhub.domain.usuario.Usuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity cadastrar(@RequestBody @Valid TopicoDTOEntrada topicoDados,
                                    @AuthenticationPrincipal Usuario usuario,
                                    UriComponentsBuilder uriBuilder) {
        var topico = service.cadastrar(usuario, topicoDados);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.id()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }

    @GetMapping
    public ResponseEntity<Page<TopicoDTOSaida>> listar(@PageableDefault(size = 10, sort = {"data_criacao"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        var pagina = service.listar(paginacao);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var topico = service.detalhar(id);
        return ResponseEntity.ok(topico);
    }

    @PutMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity atualizar(@PathVariable Long id,
                                    @RequestBody @Valid TopicoDTOAtualizacao topicoDados,
                                    @AuthenticationPrincipal Usuario usuario) {
        var topico = service.atualizar(id, topicoDados, usuario);
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity excluir(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        service.excluir(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
