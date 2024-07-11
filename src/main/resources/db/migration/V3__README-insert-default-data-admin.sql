INSERT INTO perfis(id, nome) VALUES(1, "ROLE_ADM");
INSERT INTO perfis(id, nome) VALUES(2, "USER");
INSERT INTO perfis(id, nome) VALUES(3, "PROFESSOR");

-- README -> LOGIN DO USUARIO ROOT: admin@forumhubalura.com | SENHA DO USUARIO ROOT: root1234
-- RECOMENDADO ATUALIZAR O USUÁRIO COM UM NOVO EMAIL DE LOGIN E SENHA
INSERT INTO usuarios(nome, email, senha) VALUES("root", "admin@forumhubalura.com", "$2a$12$/UBhb03fEEeMt/kMOnw13uBoAt7c4HKrxbqXGg1G8gci579BCBhh6");
INSERT INTO usuarios_perfis(usuario_id, perfil_id) VALUES(1, 1);