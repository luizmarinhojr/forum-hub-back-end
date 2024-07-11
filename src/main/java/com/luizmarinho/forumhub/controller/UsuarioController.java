package com.luizmarinho.forumhub.controller;

import com.luizmarinho.forumhub.domain.usuario.UsuarioDTOAtualizacao;
import com.luizmarinho.forumhub.domain.usuario.UsuarioDTOEntrada;
import com.luizmarinho.forumhub.domain.usuario.UsuarioDTOPatch;
import com.luizmarinho.forumhub.domain.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @PutMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity atualizar(@RequestBody UsuarioDTOAtualizacao usuarioAtualizacao, Authentication authentication) {
        var usuario = service.atualizar(usuarioAtualizacao, authentication);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity detalhar(Authentication authentication) {
        var usuario = service.detalhar(authentication);
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/{usuario_id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity atualizarPerfilUsuarioEspecifico(@PathVariable("usuario_id") Long usuarioId, @RequestBody @Valid UsuarioDTOPatch usuarioPatch, Authentication authentication) {
        var usuario = service.atualizarPerfilUsuarioEspecifico(usuarioId, usuarioPatch, authentication);
        return ResponseEntity.ok(usuario);
    }
}
