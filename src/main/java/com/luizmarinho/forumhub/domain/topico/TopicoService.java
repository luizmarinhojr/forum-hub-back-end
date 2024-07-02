package com.luizmarinho.forumhub.domain.topico;

import com.luizmarinho.forumhub.domain.ValidacaoException;
import com.luizmarinho.forumhub.domain.curso.CursoRepository;
import com.luizmarinho.forumhub.domain.topico.validacoes.cadastro.ValidadorDuplicidadeTopico;
import com.luizmarinho.forumhub.domain.topico.validacoes.cadastro.ValidadorInterface;
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
    private List<ValidadorInterface> validadores;

    public TopicoDTOSaida cadastrar(TopicoDTOEntrada topicoEntrada) {
        validadores.forEach(d -> d.validar(topicoEntrada));
        var curso = cursoRepository.getReferenceById(topicoEntrada.cursoId());
        var usuario = usuarioRepository.getReferenceById(topicoEntrada.autorId());
        Topico topico = new Topico(topicoEntrada, usuario, curso);
        topicoRepository.save(topico);

        return new TopicoDTOSaida(topico);
    }

    public Page<TopicoDTOSaida> listar(Pageable paginacao) {
        var page = topicoRepository.buscarPorTodosTopicosAtivos(paginacao).map(TopicoDTOSaida::new);
        return page;
    }

    public TopicoDTOSaida detalhar(Long id) {
        if (id <= 0 || !topicoRepository.existsById(id)) {
            throw new ValidacaoException("Id do tópico informado não existe");
        }
        return new TopicoDTOSaida(topicoRepository.getReferenceById(id));
    }
}
