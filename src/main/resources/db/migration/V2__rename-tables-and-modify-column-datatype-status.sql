RENAME TABLE curso TO cursos;
RENAME TABLE perfil TO perfis;
RENAME TABLE resposta TO respostas;
RENAME TABLE topico TO topicos;
RENAME TABLE usuario TO usuarios;
RENAME TABLE usuario_perfis TO usuarios_perfis;

ALTER TABLE topicos
MODIFY COLUMN status VARCHAR(120) NOT NULL;

UPDATE topicos
SET status = "NAO_RESPONDIDO";

ALTER TABLE topicos
ADD solucao_resposta_id BIGINT;

ALTER TABLE topicos ADD CONSTRAINT fk_topicos_respostas_id
FOREIGN KEY(solucao_resposta_id) REFERENCES respostas(id);