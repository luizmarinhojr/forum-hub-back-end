package com.luizmarinho.forumhub.domain.resposta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luizmarinho.forumhub.domain.topico.Topico;
import com.luizmarinho.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;

    @JsonProperty(value = "topico_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @JsonProperty(value = "data_criacao")
    private LocalDateTime dataCriacao;

    @JsonProperty(value = "autor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    private String solucao;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "solucaoResposta")
    private Topico solucaoTopico;

    public Resposta(RespostaDTOEntrada respostaEntrada, Topico topico, Usuario autor) {
        this.mensagem = respostaEntrada.mensagem();
        this.topico = topico;
        this.dataCriacao = LocalDateTime.now();
        this.autor = autor;
        this.solucao = respostaEntrada.solucao();
    }
}
