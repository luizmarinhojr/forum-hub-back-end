package com.luizmarinho.forumhub.domain.perfil;

import com.luizmarinho.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "perfis")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "perfis", fetch = FetchType.EAGER)
    private Set<Usuario> usuarios = new HashSet<>();

    public Perfil(PerfilDTOEntrada perfilEntrada) {
        this.nome = perfilEntrada.nome();
    }
}
