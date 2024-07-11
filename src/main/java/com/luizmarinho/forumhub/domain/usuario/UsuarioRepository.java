package com.luizmarinho.forumhub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM usuarios WHERE email = :email", nativeQuery = true)
    Optional<Usuario> buscarUsuarioPorEmail(@Param("email") String email);

    UserDetails findByEmail(String email);
}
