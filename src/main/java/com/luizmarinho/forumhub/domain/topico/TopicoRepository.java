package com.luizmarinho.forumhub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    @Query(value = "SELECT * FROM topicos WHERE titulo = :titulo", nativeQuery = true)
    Optional<Topico> buscarPorTituloIgual(@Param("titulo") String titulo);

    @Query(value = "SELECT * FROM topicos WHERE mensagem = :mensagem", nativeQuery = true)
    Optional<Topico> buscarPorMensagemIgual(@Param("mensagem") String mensagem);

    @Query(value = "SELECT * FROM topicos", nativeQuery = true)
    Page<Topico> buscarPorTodosTopicos(Pageable paginacao);

//    @Query(value = "UPDATE topicos SET status = :status WHERE id = :id", nativeQuery = true)
//    Topico atualizarTopicoPorId(@Param("status") StatusEnum status, @Param("id") Long id);
}
