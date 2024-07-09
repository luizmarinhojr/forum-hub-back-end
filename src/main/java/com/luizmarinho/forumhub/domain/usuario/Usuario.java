package com.luizmarinho.forumhub.domain.usuario;

import com.luizmarinho.forumhub.domain.perfil.Perfil;
import com.luizmarinho.forumhub.domain.resposta.Resposta;
import com.luizmarinho.forumhub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
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

    @ManyToMany
    @JoinTable(name = "usuario_perfis",
            joinColumns = @JoinColumn(name="usuario_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="perfil_id", nullable = false))
    private Set<Perfil> perfis;

    public Usuario(UsuarioDTOEntrada usuarioEntrada) {
        this.nome = usuarioEntrada.nome();
        this.email = usuarioEntrada.email();
        this.senha = usuarioEntrada.senha();
    }

    public Usuario(UsuarioDTOEntrada usuarioEntrada, String senha) {
        this.nome = usuarioEntrada.nome();
        this.email = usuarioEntrada.email();
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
