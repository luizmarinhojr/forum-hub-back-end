package com.luizmarinho.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luizmarinho.forumhub.domain.curso.Curso;
import com.luizmarinho.forumhub.domain.resposta.Resposta;
import com.luizmarinho.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "topico")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    @JsonProperty(value = "data_criacao")
    private LocalDateTime dataCriacao;

    private Boolean status;

    @JsonProperty(value = "autor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @JsonProperty(value = "curso_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico")
    private Set<Resposta> respostas;

    public Topico(TopicoDTOEntrada data) {
        this.titulo = data.titulo();
        this.mensagem = data.mensagem();
        this.autor = data.autor();
        this.curso = data.curso();
        this.dataCriacao = LocalDateTime.now();
        this.status = true;
    }
}
