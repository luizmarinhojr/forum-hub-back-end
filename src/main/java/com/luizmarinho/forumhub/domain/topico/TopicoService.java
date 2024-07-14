package com.luizmarinho.forumhub.domain.topico;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
import com.luizmarinho.forumhub.domain.exception.ValidacaoExceptionAuthorization;
import com.luizmarinho.forumhub.domain.exception.ValidacaoExceptionNotFound;
import com.luizmarinho.forumhub.domain.curso.CursoRepository;
import com.luizmarinho.forumhub.domain.perfil.Perfil;
import com.luizmarinho.forumhub.domain.resposta.Resposta;
import com.luizmarinho.forumhub.domain.resposta.RespostaRepository;
import com.luizmarinho.forumhub.domain.topico.validacoes.atualizacao.ValidadorAtualizacao;
import com.luizmarinho.forumhub.domain.topico.validacoes.cadastro.ValidadorCadastro;
import com.luizmarinho.forumhub.domain.usuario.Usuario;
import com.luizmarinho.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private List<ValidadorCadastro> validadoresCadastro;

    @Autowired
    private List<ValidadorAtualizacao> validadoresAtualizacao;


    public TopicoDTOSaida cadastrar(Usuario usuarioAutenticado, TopicoDTOEntrada topicoEntrada) {
        validadoresCadastro.forEach(d -> d.validar(topicoEntrada));
        var curso = cursoRepository.getReferenceById(topicoEntrada.cursoId());
        var usuario = usuarioRepository.getReferenceById(usuarioAutenticado.getId());
        Topico topico = new Topico(topicoEntrada, usuario, curso);
        topicoRepository.save(topico);

        return new TopicoDTOSaida(topico);
    }

    public Page<TopicoDTOSaida> listar(Pageable paginacao) {
        return topicoRepository.buscarPorTodosTopicos(paginacao).map(TopicoDTOSaida::new);
    }

    public TopicoDTODetalharSaida detalhar(Long id) {
        var topico = verificarTopicoId(id);
        return new TopicoDTODetalharSaida(topico);
    }

    public TopicoDTOSaida atualizar(Long id, TopicoDTOAtualizacao topicoAtualizacao, Usuario usuarioAutenticado) {
        validadoresAtualizacao.forEach(x -> x.validar(topicoAtualizacao));
        var topico = verificarTopicoId(id);
        boolean ehAdmin = verificarIsAdmin(usuarioAutenticado);

        if (!ehAdmin && !topico.getAutor().getId().equals(usuarioAutenticado.getId())) {
            throw new ValidacaoExceptionAuthorization("O usuário não possui permissão para alterar o tópico");
        }

        if (topicoAtualizacao.cursoId() != null) {
            var curso = cursoRepository.getReferenceById(topicoAtualizacao.cursoId());
            topico.setCurso(curso);
        }

        topico.atualizar(topicoAtualizacao);

        verificarStatusESolucaoId(topicoAtualizacao);
        if (topicoAtualizacao.status().equals(StatusEnum.SOLUCIONADO)) {
            var resposta = respostaRepository.buscarRespostaPorTopico(id, topicoAtualizacao.solucaoId());
            verificarRespostaId(resposta);
            topico.setSolucaoResposta(resposta.get());
        }

        return new TopicoDTOSaida(topico);
    }

    public void excluir(Long id, Usuario usuarioAutenticado) {
        var topicoBd = verificarTopicoId(id);
        boolean isAdmin = verificarIsAdmin(usuarioAutenticado);
        if (isAdmin || topicoBd.getAutor().getId().equals(usuarioAutenticado.getId())) {
            topicoRepository.deleteById(id);
        } else {
            throw new ValidacaoExceptionAuthorization("O usuário não possui permissão para excluir o tópico");
        }
    }

    private Topico verificarTopicoId(Long id) {
        var topicoBd = topicoRepository.findById(id);
        if (topicoBd.isEmpty()) {
            throw new ValidacaoExceptionNotFound("Id do tópico informado não existe");
        }
        return topicoBd.get();
    }

    private void verificarStatusESolucaoId(TopicoDTOAtualizacao topicoAtualizacao) {
        if (topicoAtualizacao.status().equals(StatusEnum.SOLUCIONADO) && topicoAtualizacao.solucaoId() == null) {
            throw new ValidacaoException("É necessário especificar o id da resposta da solução");
        }
    }

    private void verificarRespostaId(Optional<Resposta> resposta) {
        if (resposta.isEmpty()) {
            throw new ValidacaoExceptionNotFound("Não existe resposta com o id informado");
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
}
