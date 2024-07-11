package com.luizmarinho.forumhub.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query(value = "SELECT * FROM cursos WHERE nome = :nome", nativeQuery = true)
    Optional<Curso> buscarPorNomeIgual(@Param("nome") String nome);
}
