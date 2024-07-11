package com.luizmarinho.forumhub.domain.resposta.validacoes.cadastro;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.exception.ValidacaoExceptionNotFound;
import com.luizmarinho.forumhub.domain.resposta.RespostaDTOEntrada;
import com.luizmarinho.forumhub.domain.resposta.RespostaRepository;
import com.luizmarinho.forumhub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDuplicidadeResposta implements ValidadorRespostaCadastro{

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    TopicoRepository topicoRepository;

    @Override
    public void validar(RespostaDTOEntrada respostaEntrada) {
        if (topicoRepository.existsById(respostaEntrada.topicoId())) {
            var mensagem = respostaRepository.buscarPorMensagemIgual(respostaEntrada.topicoId(), respostaEntrada.mensagem());
            var solucao = respostaRepository.buscarPorSolucaoIgual(respostaEntrada.topicoId(), respostaEntrada.solucao());

            if (mensagem.isPresent()) {
                throw new ValidacaoException("Já existe uma resposta com essa mensagem");
            }

            if (solucao.isPresent()) {
                throw new ValidacaoException("Já existe uma resposta com essa solução");
            }
        } else {
            throw new ValidacaoExceptionNotFound("Id do tópico informado não existe");
        }

    }
}
