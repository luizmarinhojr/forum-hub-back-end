package com.luizmarinho.forumhub.domain.topico;

import com.luizmarinho.forumhub.domain.exception.ValidacaoException;
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
import org.springframework.security.core.Authentication;
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


    public TopicoDTOSaida cadastrar(Authentication authentication, TopicoDTOEntrada topicoEntrada) {
        validadoresCadastro.forEach(d -> d.validar(topicoEntrada));
        var curso = cursoRepository.getReferenceById(topicoEntrada.cursoId());
        var usuario = usuarioRepository.getReferenceById(((Usuario) authentication.getPrincipal()).getId());
        Topico topico = new Topico(topicoEntrada, usuario, curso);
        topicoRepository.save(topico);

        return new TopicoDTOSaida(topico);
    }

    public Page<TopicoDTOSaida> listar(Pageable paginacao) {
        return topicoRepository.buscarPorTodosTopicos(paginacao).map(TopicoDTOSaida::new);
    }

    public TopicoDTODetalharSaida detalhar(Long id) {
        verificarTopicoId(id);
        return new TopicoDTODetalharSaida(topicoRepository.getReferenceById(id));
    }

    public TopicoDTOSaida atualizar(Long id, TopicoDTOAtualizacao topicoAtualizacao, Authentication authentication) {
        validadoresAtualizacao.forEach(x -> x.validar(topicoAtualizacao));
        verificarTopicoId(id);
        var topicoBd = topicoRepository.getReferenceById(id);
        var usuario = (Usuario) authentication.getPrincipal();
        boolean ehAdmin = verificarIsAdmin(usuario);

        if (!ehAdmin && !topicoBd.getAutor().getId().equals(usuario.getId())) {
            throw new ValidacaoException("O usuário não possui permissão para alterar o tópico");
        }

        if (topicoAtualizacao.cursoId() != null) {
            var curso = cursoRepository.getReferenceById(topicoAtualizacao.cursoId());
            topicoBd.setCurso(curso);
        }

        topicoBd.atualizar(topicoAtualizacao);

        verificarStatusESolucaoId(topicoAtualizacao);
        if (topicoAtualizacao.status().equals(StatusEnum.SOLUCIONADO)) {
            var resposta = respostaRepository.buscarRespostaPorTopico(id, topicoAtualizacao.solucaoId());
            verificarRespostaId(resposta);
            topicoBd.setSolucaoResposta(resposta.get());
        }

        return new TopicoDTOSaida(topicoBd);
    }

    public void excluir(Long id) {
        verificarTopicoId(id);
        topicoRepository.deleteById(id);
    }

    private void verificarTopicoId(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ValidacaoExceptionNotFound("Id do tópico informado não existe");
        }
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
