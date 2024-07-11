package com.luizmarinho.forumhub.domain.usuario;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.perfil.Perfil;
import com.luizmarinho.forumhub.domain.perfil.PerfilRepository;
import com.luizmarinho.forumhub.domain.usuario.validacoes.atualizacao.ValidadorUsuarioAtualizacao;
import com.luizmarinho.forumhub.domain.usuario.validacoes.cadastro.ValidadorUsuarioCadastro;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
        var usuarios = usuarioRepository.count();

        // O primeiro usuario cadastrado será de ROLE_ADMIN (Administrador)
        var perfil = usuarios == 0 ? perfilRepository.getReferenceById(1L) : perfilRepository.getReferenceById(2L);
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
            boolean isAdmin = false;
            for (Perfil p : usuario.getPerfis()) {
                if (p.getNome().equals("ROLE_ADM")) {
                    isAdmin = true;
                    break;
                }
            }
            if (isAdmin) {
                for (Long id : usuarioAtualizacao.perfisId()) {
                    var perfil = perfilRepository.findById(id);
                    perfil.ifPresent(usuario::adicionarPerfil);
                }
            } else {
                throw new ValidacaoException("O usuário não possui permissão para atualizar o perfil");
            }
        }
        validadoresAtualizacao.forEach(d -> d.validar(usuarioAtualizacao));
        usuario.atualizarUsuario(usuarioAtualizacao);
        usuarioRepository.saveAndFlush(usuario);
        return new UsuarioDTOSaida(usuario);

    }
}
