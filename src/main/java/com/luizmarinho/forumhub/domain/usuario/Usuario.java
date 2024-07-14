package com.luizmarinho.forumhub.domain.usuario;

import com.luizmarinho.forumhub.domain.perfil.Perfil;
import com.luizmarinho.forumhub.domain.resposta.Resposta;
import com.luizmarinho.forumhub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "usuarios")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "autor")
    private Set<Topico> topicos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "autor")
    private Set<Resposta> resposta;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_perfis",
            joinColumns = @JoinColumn(name="usuario_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="perfil_id", nullable = false))
    private Set<Perfil> perfis = new HashSet<>();


    public Usuario(UsuarioDTOEntrada usuarioEntrada) {
        this.nome = usuarioEntrada.nome();
        this.email = usuarioEntrada.email();
        this.senha = usuarioEntrada.senha();
    }

    public Usuario(UsuarioDTOEntrada usuarioEntrada, String senha, Perfil perfil) {
        this.nome = usuarioEntrada.nome();
        this.email = usuarioEntrada.email();
        this.senha = senha;
        this.perfis.add(perfil);
    }

    public void adicionarPerfil(Perfil perfil) {
        this.perfis.add(perfil);
    }

    public void atualizarUsuario(UsuarioDTOAtualizacao usuarioAtualizacao) {
        if (usuarioAtualizacao.email() != null) {
            this.email = usuarioAtualizacao.email();
        }
        if (usuarioAtualizacao.nome() != null) {
            this.nome = usuarioAtualizacao.nome();
        }
        if (usuarioAtualizacao.senha() != null) {
            this.senha = usuarioAtualizacao.senha();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis.stream()
                        .map(perfil -> new SimpleGrantedAuthority(perfil.getNome()))
                        .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return getSenha();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
