package com.luizmarinho.forumhub.domain.resposta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {

    @Query(value = "SELECT * FROM resposta WHERE topico_id = :topicoId and mensagem = :mensagem", nativeQuery = true)
    Optional<Resposta> buscarPorMensagemIgual(@Param("topicoId") Long topicoId, @Param("mensagem") String mensagem);

    @Query(value = "SELECT * FROM resposta WHERE topico_id = :topicoId and solucao = :solucao", nativeQuery = true)
    Optional<Resposta> buscarPorSolucaoIgual(@Param("topicoId") Long topicoId, @Param("solucao") String solucao);

    @Query(value = "SELECT * FROM resposta WHERE topico_id = :topicoId", nativeQuery = true)
    Page<Resposta> buscarPorTodasRespostas(@Param("topicoId") Long topicoId, Pageable paginacao);

    @Query(value = "SELECT * FROM resposta WHERE topico_id = :topicoId and id = :respostaId", nativeQuery = true)
    Optional<Resposta> buscarRespostaPorTopico(@Param("topicoId") Long topicoId, @Param("respostaId") Long respostaId);
}
