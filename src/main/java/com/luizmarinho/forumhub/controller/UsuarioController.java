package com.luizmarinho.forumhub.controller;

import com.luizmarinho.forumhub.domain.usuario.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity cadastrar(@RequestBody @Valid UsuarioDTOEntrada usuarioEntrada,
                                    UriComponentsBuilder uriBuilder)
    {
        var usuario = service.cadastrar(usuarioEntrada);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @PutMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity atualizar(@RequestBody UsuarioDTOAtualizacao usuarioAtualizacao,
                                    @AuthenticationPrincipal Usuario usuario) {
        var usuarioDto = service.atualizar(usuarioAtualizacao, usuario);
        return ResponseEntity.ok(usuarioDto);
    }

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity detalhar(@AuthenticationPrincipal Usuario usuarioAutenticado) {
        var usuario = service.detalhar(usuarioAutenticado);
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/{usuario_id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    @Secured("ROLE_ADM")
    public ResponseEntity atualizarPerfilUsuarioEspecifico(@PathVariable("usuario_id") Long usuarioId,
                                                           @RequestBody @Valid UsuarioDTOPatch usuarioPatch,
                                                           @AuthenticationPrincipal Usuario usuarioAutenticado) {
        var usuario = service.atualizarPerfilUsuarioEspecifico(usuarioId, usuarioPatch, usuarioAutenticado);
        return ResponseEntity.ok(usuario);
    }
}
