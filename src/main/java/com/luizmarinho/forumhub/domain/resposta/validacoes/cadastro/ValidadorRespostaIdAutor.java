package com.luizmarinho.forumhub.domain.resposta.validacoes.cadastro;

import com.luizmarinho.forumhub.domain.exception.ValidacaoExceptionNotFound;
import com.luizmarinho.forumhub.domain.resposta.RespostaDTOEntrada;
import com.luizmarinho.forumhub.domain.usuario.Usuario;
import com.luizmarinho.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ValidadorRespostaIdAutor implements ValidadorRespostaCadastro{

    @Autowired
    UsuarioRepository repository;

    @Override
    public void validar(RespostaDTOEntrada respostaEntrada) {
        var authentication = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!repository.existsById(authentication.getId())) {
            throw new ValidacaoExceptionNotFound("Id do autor informado não existe");
        }
    }
}
