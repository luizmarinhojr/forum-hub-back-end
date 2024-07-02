package com.luizmarinho.forumhub.domain.topico;

import com.luizmarinho.forumhub.domain.ValidacaoException;
import com.luizmarinho.forumhub.domain.curso.CursoRepository;
import com.luizmarinho.forumhub.domain.topico.validacoes.cadastro.ValidadorDuplicidadeTopico;
import com.luizmarinho.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private ValidadorDuplicidadeTopico validarDuplicidade;

    public TopicoDTOSaida cadastrar(TopicoDTOEntrada topicoEntrada) {
        verificarIdAutor(topicoEntrada.autorId());
        verificarIdCurso(topicoEntrada.cursoId());
        Topico topico;

        validarDuplicidade.validar(topicoEntrada);
        var curso = cursoRepository.getReferenceById(topicoEntrada.cursoId());
        var usuario = usuarioRepository.getReferenceById(topicoEntrada.autorId());
        topico = new Topico(topicoEntrada, usuario, curso);
        topicoRepository.save(topico);

        return new TopicoDTOSaida(topico);
    }

    public Page<TopicoDTOSaida> listar(Pageable paginacao) {
        var pages = topicoRepository.buscarPorTodosTopicosAtivos(paginacao).map(TopicoDTOSaida::new);
        return pages;
    }

    public TopicoDTOSaida detalhar(Long id) {
        if (id <= 0 || !topicoRepository.existsById(id)) {
            throw new ValidacaoException("Id do tópico informado não existe");
        }
        return new TopicoDTOSaida(topicoRepository.getReferenceById(id));
    }

    private void verificarIdAutor(Long autorId) {
        if (autorId == null || !usuarioRepository.existsById(autorId)) {
            throw new ValidacaoException("Id do autor informado não existe");
        }
    }

    private void verificarIdCurso(Long cursoId) {
        if (cursoId == null || !cursoRepository.existsById(cursoId)) {
            throw new ValidacaoException("Id do curso informado não existe");
        }
    }
}
