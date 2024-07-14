package com.luizmarinho.forumhub.domain.resposta;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.exception.ValidacaoExceptionAuthorization;
import com.luizmarinho.forumhub.domain.exception.ValidacaoExceptionNotFound;
import com.luizmarinho.forumhub.domain.perfil.Perfil;
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
import java.util.Optional;

@Service
public class RespostaService {

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    List<ValidadorRespostaCadastro> validadores;

    public RespostaDTOSaida cadastrar(RespostaDTOEntrada respostaEntrada, Long topicoId, Usuario usuario) {
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

    public void excluir(Long id, Usuario usuario) {
        var resposta = respostaRepository.findById(id);
        verificarRespostaId(resposta);
        boolean isAdmin = verificarIsAdmin(usuario);
        if (isAdmin || usuario.getId().equals(resposta.get().getAutor().getId())) {
            boolean respostaIsDiferenteNull = resposta.get().getTopico().getSolucaoResposta() != null;
            if (respostaIsDiferenteNull) {
                Long solucaoResposta = resposta.get().getTopico().getSolucaoResposta().getId();
                if (solucaoResposta.equals(resposta.get().getId())) {
                    throw new ValidacaoException("Não é possível remover uma resposta que está marcada como solução de um tópico");
                }
            }
            respostaRepository.delete(resposta.get());
        } else {
            throw new ValidacaoExceptionAuthorization("O usuário não possui permissão para excluir a resposta");
        }
    }

    private boolean verificarIsAdmin(Usuario usuario) {
        for (Perfil perfil : usuario.getPerfis()) {
            if(perfil.getNome().equals("ROLE_ADM")) {
                return true;
            }
        }
        return false;
    }

    private void verificarRespostaId(Optional<Resposta> resposta) {
        if (resposta.isEmpty()) {
            throw new ValidacaoExceptionNotFound("Id da resposta informado não existe");
        }
    }
}
