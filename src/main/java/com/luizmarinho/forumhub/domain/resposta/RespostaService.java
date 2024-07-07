package com.luizmarinho.forumhub.domain.resposta;

import com.luizmarinho.forumhub.domain.ValidacaoException;
import com.luizmarinho.forumhub.domain.ValidacaoExceptionNotFound;
import com.luizmarinho.forumhub.domain.resposta.validacoes.cadastro.ValidadorRespostaCadastro;
import com.luizmarinho.forumhub.domain.topico.TopicoRepository;
import com.luizmarinho.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespostaService {

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    List<ValidadorRespostaCadastro> validadores;

    public RespostaDTOSaida cadastrar(RespostaDTOEntrada respostaEntrada, Long topicoId) {
        var respostaDTO = new RespostaDTOEntrada(respostaEntrada.mensagem(), topicoId, respostaEntrada.usuarioId(), respostaEntrada.solucao());
        validadores.forEach(d -> d.validar(respostaDTO));

        var usuario = usuarioRepository.getReferenceById(respostaDTO.usuarioId());
        var topico = topicoRepository.getReferenceById(respostaDTO.topicoId());
        var resposta = new Resposta(respostaDTO, topico, usuario);
        respostaRepository.save(resposta);

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
