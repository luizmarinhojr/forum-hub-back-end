package com.luizmarinho.forumhub.domain.resposta;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.exception.ValidacaoExceptionNotFound;
import com.luizmarinho.forumhub.domain.resposta.validacoes.cadastro.ValidadorRespostaCadastro;
import com.luizmarinho.forumhub.domain.topico.StatusEnum;
import com.luizmarinho.forumhub.domain.topico.TopicoRepository;
import com.luizmarinho.forumhub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespostaService {

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    List<ValidadorRespostaCadastro> validadores;

    public RespostaDTOSaida cadastrar(RespostaDTOEntrada respostaEntrada, Long topicoId, Authentication authentication) {
        var usuario = (Usuario) authentication.getPrincipal();
        var respostaDTO = new RespostaDTOEntrada(respostaEntrada.mensagem(), topicoId, respostaEntrada.solucao());
        validadores.forEach(d -> d.validar(respostaDTO));
        var topico = topicoRepository.getReferenceById(respostaDTO.topicoId());
        if (topico.getStatus().equals(StatusEnum.SOLUCIONADO)) {
            throw new ValidacaoException("Não é possível cadastrar novas respostas para um tópico já solucionado");
        }
        var resposta = new Resposta(respostaDTO, topico, usuario);
        respostaRepository.save(resposta);

        if (topico.getStatus().equals(StatusEnum.NAO_RESPONDIDO)) {
            topico.setStatus(StatusEnum.RESPONDIDO);
        }

        return new RespostaDTOSaida(resposta);
    }

    public Page<RespostaDTOSaida> listar(Pageable paginacao, Long topicoId) {
        if (topicoRepository.existsById(topicoId)) {
            return respostaRepository.buscarPorTodasRespostas(topicoId, paginacao).map(RespostaDTOSaida::new);
        }
        throw new ValidacaoExceptionNotFound("Id do tópico informado não existe");
    }

    public RespostaDTOSaida detalhar(Long topicoId, Long respostaId) {
        if (!topicoRepository.existsById(topicoId)) {
            throw new ValidacaoExceptionNotFound("Id do tópico informado não existe");
        }

        var resposta = respostaRepository.buscarRespostaPorTopico(topicoId, respostaId);

        if (resposta.isPresent()) {
            return new RespostaDTOSaida(resposta.get());
        }

        throw new ValidacaoExceptionNotFound("Id da resposta informado não existe ou não pertence a esse tópico");
    }
}
