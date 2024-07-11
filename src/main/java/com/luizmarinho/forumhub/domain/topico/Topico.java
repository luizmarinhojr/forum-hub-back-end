package com.luizmarinho.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luizmarinho.forumhub.domain.curso.Curso;
import com.luizmarinho.forumhub.domain.resposta.Resposta;
import com.luizmarinho.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "topicos")
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

    @Enumerated(EnumType.STRING)
    @Setter
    private StatusEnum status;

    @JsonProperty(value = "autor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @JsonProperty(value = "curso_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    @Setter
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.REMOVE)
    private Set<Resposta> respostas;

    @JsonProperty("solucao_resposta_id")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solucao_resposta_id")
    @Setter
    private Resposta solucaoResposta;

    public Topico(TopicoDTOEntrada dado, Usuario autor, Curso curso) {
        this.titulo = dado.titulo();
        this.mensagem = dado.mensagem();
        this.autor = autor;
        this.curso = curso;
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusEnum.NAO_RESPONDIDO;
    }

    protected void atualizar(TopicoDTOAtualizacao topicoAtualizacao) {
        if (topicoAtualizacao.titulo() != null) {
            this.titulo = topicoAtualizacao.titulo();
        }

        if (topicoAtualizacao.mensagem() != null) {
            this.mensagem = topicoAtualizacao.mensagem();
        }

        if (topicoAtualizacao.status() != null) {
            this.status = topicoAtualizacao.status();
        }
    }
}
