package com.luizmarinho.forumhub.domain.topico;

import com.luizmarinho.forumhub.domain.ValidacaoExceptionNotFound;
import com.luizmarinho.forumhub.domain.curso.CursoRepository;
import com.luizmarinho.forumhub.domain.topico.validacoes.atualizacao.ValidadorAtualizacao;
import com.luizmarinho.forumhub.domain.topico.validacoes.cadastro.ValidadorCadastro;
import com.luizmarinho.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private List<ValidadorCadastro> validadoresCadastro;

    @Autowired
    private List<ValidadorAtualizacao> validadoresAtualizacao;


    public TopicoDTOSaida cadastrar(TopicoDTOEntrada topicoEntrada) {
        validadoresCadastro.forEach(d -> d.validar(topicoEntrada));
        var curso = cursoRepository.getReferenceById(topicoEntrada.cursoId());
        var usuario = usuarioRepository.getReferenceById(topicoEntrada.autorId());
        Topico topico = new Topico(topicoEntrada, usuario, curso);
        topicoRepository.save(topico);

        return new TopicoDTOSaida(topico);
    }

    public Page<TopicoDTOSaida> listar(Pageable paginacao) {
        return topicoRepository.buscarPorTodosTopicos(paginacao).map(TopicoDTOSaida::new);
    }

    public TopicoDTOSaida detalhar(Long id) {
        verificarTopicoId(id);
        return new TopicoDTOSaida(topicoRepository.getReferenceById(id));
    }

    public TopicoDTOSaida atualizar(Long id, TopicoDTOAtualizacao topicoAtualizacao) {
        validadoresAtualizacao.forEach(x -> x.validar(topicoAtualizacao));
        verificarTopicoId(id);
        var topicoBd = topicoRepository.getReferenceById(id);

        if (topicoAtualizacao.cursoId() != null) {
            var curso = cursoRepository.getReferenceById(topicoAtualizacao.cursoId());
            topicoBd.setCurso(curso);
        }

        topicoBd.atualizar(topicoAtualizacao);

        return new TopicoDTOSaida(topicoBd);

    }

    public void excluir(Long id) {
        verificarTopicoId(id);
        var topico = topicoRepository.getReferenceById(id);
        topico.excluir();
    }

    private void verificarTopicoId(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ValidacaoExceptionNotFound("Id do tópico informado não existe");
        }
    }
}
