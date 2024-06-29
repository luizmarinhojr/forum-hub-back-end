package com.luizmarinho.forumhub.domain.usuario;

import com.luizmarinho.forumhub.domain.perfil.Perfil;
import com.luizmarinho.forumhub.domain.resposta.Resposta;
import com.luizmarinho.forumhub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "usuario")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

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
}
