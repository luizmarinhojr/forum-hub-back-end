CREATE TABLE curso (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE topico (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(200) NOT NULL,
    mensagem TEXT NOT NULL,
    data_criacao DATETIME NOT NULL,
    status TINYINT NOT NULL,
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE resposta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensagem TEXT NOT NULL,
    topico_id BIGINT NOT NULL,
    data_criacao DATETIME NOT NULL,
    autor_id BIGINT NOT NULL,
    solucao TEXT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(200) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE perfil (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(200) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE usuario_perfis (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY(usuario_id, perfil_id)
);

ALTER TABLE topico ADD CONSTRAINT fk_topico_autor_id
FOREIGN KEY(autor_id) REFERENCES usuario(id);

ALTER TABLE topico ADD CONSTRAINT fk_topico_curso_id
FOREIGN KEY(curso_id) REFERENCES curso(id);

ALTER TABLE resposta ADD CONSTRAINT fk_resposta_topico_id
FOREIGN KEY(topico_id) REFERENCES topico(id);

ALTER TABLE resposta ADD CONSTRAINT fk_resposta_autor_id
FOREIGN KEY(autor_id) REFERENCES usuario(id);

ALTER TABLE usuario_perfis ADD CONSTRAINT fk_usuario_id
FOREIGN KEY(usuario_id) REFERENCES usuario(id);

ALTER TABLE usuario_perfis ADD CONSTRAINT fk_perfil_id
FOREIGN KEY(perfil_id) REFERENCES perfil(id);