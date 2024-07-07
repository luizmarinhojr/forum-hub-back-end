package com.luizmarinho.forumhub.domain.usuario.validacoes.cadastro;

import com.luizmarinho.forumhub.domain.usuario.UsuarioDTOEntrada;

public interface ValidadorUsuarioCadastro {
    void validar(UsuarioDTOEntrada usuarioEntrada);
}
