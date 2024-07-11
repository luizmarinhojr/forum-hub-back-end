package com.luizmarinho.forumhub.domain.usuario;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.exception.ValidacaoExceptionAuthorization;
import com.luizmarinho.forumhub.domain.perfil.Perfil;
import com.luizmarinho.forumhub.domain.perfil.PerfilRepository;
import com.luizmarinho.forumhub.domain.usuario.validacoes.atualizacao.ValidadorUsuarioAtualizacao;
import com.luizmarinho.forumhub.domain.usuario.validacoes.cadastro.ValidadorUsuarioCadastro;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private List<ValidadorUsuarioCadastro> validadoresCadastro;

    @Autowired
    private List<ValidadorUsuarioAtualizacao> validadoresAtualizacao;

    public UsuarioDTOSaida cadastrar(UsuarioDTOEntrada usuarioEntrada) {
        validadoresCadastro.forEach(d -> d.validar(usuarioEntrada));

        var perfil = perfilRepository.getReferenceById(2L);
        var senhaEncoder = passwordEncoder.encode(usuarioEntrada.senha());
        var usuario = new Usuario(usuarioEntrada, senhaEncoder, perfil);

        usuarioRepository.save(usuario);
        return new UsuarioDTOSaida(usuario);
    }

    public UsuarioDTOSaida detalhar(Authentication authentication) {
        var usuario = (Usuario) authentication.getPrincipal();
        return new UsuarioDTOSaida(usuario);
    }

    public UsuarioDTOSaida atualizar(UsuarioDTOAtualizacao usuarioAtualizacao, Authentication authentication) {
        var usuario = (Usuario) authentication.getPrincipal();
        if (usuarioAtualizacao.perfisId() != null) {
            boolean isAdmin = verificarIsAdmin(usuario);
            if (!isAdmin) {
                throw new ValidacaoExceptionAuthorization("O usuário não possui permissão para atualizar o perfil");
            }

            for (Long id : usuarioAtualizacao.perfisId()) {
                var perfil = perfilRepository.findById(id);
                perfil.ifPresent(usuario::adicionarPerfil);
            }
        }
        validadoresAtualizacao.forEach(d -> d.validar(usuarioAtualizacao));
        usuario.atualizarUsuario(usuarioAtualizacao);
        usuarioRepository.saveAndFlush(usuario);
        return new UsuarioDTOSaida(usuario);

    }

    public UsuarioDTOSaida atualizarPerfilUsuarioEspecifico(Long usuarioId, UsuarioDTOPatch usuarioPatch, Authentication authentication) {
        var usuarioAutenticado = (Usuario) authentication.getPrincipal();
        if (!verificarIsAdmin(usuarioAutenticado)) {
            throw new ValidacaoExceptionAuthorization("O usuário não possui permissão para atualizar o perfil de um usuário");
        }
        var usuarioParaAtualizar = usuarioRepository.findById(usuarioId);
        if (usuarioParaAtualizar.isEmpty()) {
            throw new ValidacaoException("Id do usuário informado não existe.");
        }
        for (Long id : usuarioPatch.perfilId()) {
            var perfil = perfilRepository.findById(id);
            if (perfil.isEmpty()) {
                throw new ValidacaoException("O id '" + id + "' é inválido ou não se referencia a um perfil válido");
            }
            usuarioParaAtualizar.get().adicionarPerfil(perfil.get());
        }
        usuarioRepository.flush();

        return new UsuarioDTOSaida(usuarioParaAtualizar.get());
    }

    private boolean verificarIsAdmin(Usuario usuario) {
        for (Perfil p : usuario.getPerfis()) {
            if (p.getNome().equals("ROLE_ADM")) {
                return true;
            }
        }
        return false;
    }
}
