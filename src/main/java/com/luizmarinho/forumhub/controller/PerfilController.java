package com.luizmarinho.forumhub.controller;

import com.luizmarinho.forumhub.domain.perfil.PerfilDTOEntrada;
import com.luizmarinho.forumhub.domain.perfil.PerfilDTOSaida;
import com.luizmarinho.forumhub.domain.perfil.PerfilService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/perfil")
@Secured("ROLE_ADM")
public class PerfilController {

    @Autowired
    private PerfilService service;

    @PostMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity cadastrar(@RequestBody @Valid PerfilDTOEntrada perfilEntrada,
                                    UriComponentsBuilder uriBuilder) {
        var perfil = service.cadastrar(perfilEntrada);
        var uri = uriBuilder.path("/perfil/{id}").buildAndExpand(perfil.getId()).toUri();
        return ResponseEntity.created(uri).body(new PerfilDTOSaida(perfil));
    }
}
