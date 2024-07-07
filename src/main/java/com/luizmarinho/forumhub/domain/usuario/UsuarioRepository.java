package com.luizmarinho.forumhub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM usuario WHERE email = :email", nativeQuery = true)
    Optional<UsuarioDTOSaida> buscarUsuarioPorEmailDTO(@Param("email") String email);

    @Query(value = "SELECT * FROM usuario WHERE email = :email", nativeQuery = true)
    Optional<Usuario> buscarUsuarioPorEmail(@Param("email") String email);
}
