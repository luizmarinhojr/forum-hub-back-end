package com.luizmarinho.forumhub.controller;

import com.luizmarinho.forumhub.domain.usuario.UsuarioDTOEntrada;
import com.luizmarinho.forumhub.domain.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(
            @RequestBody @Valid UsuarioDTOEntrada usuarioEntrada,
            UriComponentsBuilder uriBuilder)
    {
        var usuario = service.cadastrar(usuarioEntrada);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping("/{email}")
    public ResponseEntity detalhar(@PathVariable("email") String email) {
        var usuario = service.detalhar(email);
        return ResponseEntity.ok(usuario);
    }
}
