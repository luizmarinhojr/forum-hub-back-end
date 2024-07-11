package com.luizmarinho.forumhub.controller;

import com.luizmarinho.forumhub.domain.resposta.RespostaDTOEntrada;
import com.luizmarinho.forumhub.domain.resposta.RespostaDTOSaida;
import com.luizmarinho.forumhub.domain.resposta.RespostaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("topicos/{topico_id}/respostas")
public class RespostaController {

    @Autowired
    RespostaService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(
            @RequestBody @Valid RespostaDTOEntrada respostaEntrada,
            @PathVariable("topico_id") Long topicoId,
            Authentication authentication,
            UriComponentsBuilder uriBuilder)
    {
        var resposta = service.cadastrar(respostaEntrada, topicoId, authentication);

        var uri = uriBuilder.path("/resposta/{id}").buildAndExpand(resposta.id()).toUri();
        return ResponseEntity.created(uri).body(resposta);
    }

    @GetMapping
    public ResponseEntity<Page<RespostaDTOSaida>> listar(
            @PageableDefault(size = 10, sort = {"data_criacao"}, direction = Sort.Direction.DESC) Pageable paginacao,
            @PathVariable("topico_id") Long topicoId)
    {
        var pagina = service.listar(paginacao, topicoId);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{resposta_id}")
    public ResponseEntity detalhar(
            @PathVariable("topico_id") Long topicoId,
            @PathVariable("resposta_id") Long respostaId)
    {
        var resposta = service.detalhar(topicoId, respostaId);
        return ResponseEntity.ok(resposta);
    }
}
