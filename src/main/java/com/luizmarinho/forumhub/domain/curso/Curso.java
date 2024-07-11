package com.luizmarinho.forumhub.domain.curso;

import com.luizmarinho.forumhub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "cursos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String categoria;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curso")
    private Set<Topico> topicos;

    public Curso(CursoDTOEntrada cursoEntrada) {
        this.nome = cursoEntrada.nome();
        this.categoria = cursoEntrada.categoria();
    }
}
