package com.luizmarinho.forumhub.domain.topico;

import com.luizmarinho.forumhub.domain.ValidacaoException;
import com.luizmarinho.forumhub.domain.curso.CursoRepository;
import com.luizmarinho.forumhub.domain.topico.validacoes.cadastro.ValidadorDuplicidadeTopico;
import com.luizmarinho.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Topico cadastrar(TopicoDTOEntrada topicoEntrada) {
        boolean existeIdAutor = verificarIdAutor(topicoEntrada.autorId());
        boolean existeIdCurso = verificarIdCurso(topicoEntrada.cursoId());
        Topico topico = null;

        if (existeIdAutor && existeIdCurso) {
            validarDuplicidade.validar(topicoEntrada);
            var curso = cursoRepository.getReferenceById(topicoEntrada.cursoId());
            var usuario = usuarioRepository.getReferenceById(topicoEntrada.autorId());
            topico = new Topico(topicoEntrada, usuario, curso);
            topicoRepository.save(topico);
        }
        return topico;
    }

    public boolean verificarIdAutor(Long autorId) {
        if (autorId == null || !usuarioRepository.existsById(autorId)) {
            throw new ValidacaoException("Id do autor informado não existe");
        }
        return true;
    }

    public boolean verificarIdCurso(Long cursoId) {
        if (cursoId == null || !cursoRepository.existsById(cursoId)) {
            throw new ValidacaoException("Id do curso informado não existe");
        }
        return true;
    }
}
