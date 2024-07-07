package com.luizmarinho.forumhub.domain.usuario;

import com.luizmarinho.forumhub.domain.ValidacaoExceptionNotFound;
import com.luizmarinho.forumhub.domain.usuario.validacoes.cadastro.ValidadorUsuarioCadastro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    List<ValidadorUsuarioCadastro> validadores;

    public UsuarioDTOSaida cadastrar(UsuarioDTOEntrada usuarioEntrada) {
        validadores.forEach(d -> d.validar(usuarioEntrada));
        var usuario = new Usuario(usuarioEntrada);
        usuarioRepository.save(usuario);
        return new UsuarioDTOSaida(usuario);
    }

    public UsuarioDTOSaida detalhar(String email) {
        var usuario = usuarioRepository.buscarUsuarioPorEmailDTO(email);

        if (usuario.isEmpty()) {
            throw new ValidacaoExceptionNotFound("E-mail não encontrado");
        }

        return usuario.get();
    }
}
