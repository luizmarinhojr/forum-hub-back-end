package com.luizmarinho.forumhub.controller;

import com.luizmarinho.forumhub.domain.perfil.PerfilDTOEntrada;
import com.luizmarinho.forumhub.domain.perfil.PerfilDTOSaida;
import com.luizmarinho.forumhub.domain.perfil.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private PerfilService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody PerfilDTOEntrada perfilEntrada, UriComponentsBuilder uriBuilder, Authentication authentication) {
        var perfil = service.cadastrar(perfilEntrada, authentication);
        var uri = uriBuilder.path("/perfil/{id}").buildAndExpand(perfil.getId()).toUri();
        return ResponseEntity.created(uri).body(new PerfilDTOSaida(perfil));
    }

//    @GetMapping
//    public ResponseEntity listar(Authentication authentication) {
//        var perfil = service.listar(authentication);
//        return ResponseEntity.ok(perfil);
//    }
}
