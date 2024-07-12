# API Rest do Forum Hub da Alura

API para o

## Tecnologias utilizadas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

## Regras de negócio

A aplicação vem com 3 perfis ("ROLE_ADM", "USER", "PROFESSOR") e um usuário padrão pré cadastrado, que é o usuário root.

Cujo dados de login são:

Login: admin@forumhubalura.com

Senha: root1234

ATENÇÃO: É recomendado alterar os dados de login e senha do usuário root padrão logo que se inicializa a aplicação pela primeira vez.

## Alterando dados de login e senha do usuário root padrão

1. Prepare uma requisição POST para o Endpoint ``/login``
2. Coloque no corpo da requisição o seguinte Json:
   
   ```
    {
    	"email" : "admin@forumhubalura.com",
    	"senha" : "root1234"
    }
   ```
   
3. Copie o token que foi retornado no corpo da resposta da requisição e use-o para se autenticar com o Bearer Token em cada requisição que necessitar a autenticação.
4. Prepare uma requisição PUT para o Endpoint ``/usuarios``
5. Coloque no corpo da requisição o seguinte Json:
   
   ```
    {
      "email": "digite_um_email_valido",
      "senha": "digite_uma_senha_valida"
    }
   ```
6. Cole o token no campo que se refere a Auth e Bearer Token.
7. Feito isso sua senha estará alterada e sua aplicação segura.

## Diagrama do banco de dados

![Screenshot_20240711_191805](https://github.com/user-attachments/assets/acb65ae8-804d-4c55-992a-919f18081090)

<br>

## Descrição dos Endpoints

### Endpoint ``/usuarios``

**POST** : Cadastra um novo usuário no sistema. Não necessita estar cadastrado ou autenticado para enviar requisições POST para este Endpoint.
* Envio do modelo da consulta da requisição:

```
{
  "nome": "string",
  "email": "string",
  "senha": "string"
}
```

Onde: Todos os campos são obrigatórios.

<br>

**GET** : O usuário precisa estar cadastrado e autenticado para que possa detalhar o próprio perfil.
* Não é necessário inserir Json no corpo do envio da requisição.
* Resposta da consulta:

```
{
  "id": 4,
  "nome": "Jorge Fontoura Neto",
  "email": "jorge.fontoura@exemplo.com",
  "perfis": [
    "USER"
  ]
}
```

<br>

**PUT** : O usuário precisa estar cadastrado e autenticado para que possa atualizar o próprio perfil. Somente quem pode adicionar novos perfis ao próprio usuário é o usuário com perfil de administrador
* Envio do modelo da consulta da requisição:

```
{
  "nome": "string",
  "email": "string",
  "senha": "string",
  "perfis_id": [
    0
  ]
}
```

Onde: Nenhum dos campos é obrigatório

<br>

### Endpoint ``/login``

**POST** : Se autentica no sistema. Não necessita estar cadastrado ou autenticado para enviar requisições POST para este Endpoint
* Envio do modelo da consulta da requisição:

```
{
  "email": "string",
  "senha": "string"
}
```

Onde: Todos os campos são obrigatórios.

* Resposta da consulta da requisição:

```
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJBUEkgRm9ydW0gQWx1cmEiLCJzdWIiOiJqb3JnZS5mb250b3VyYUBleGVtcGxvLmNvbSIsImlkIjo0LCJleHAiOjE3MjA3NjAwNTB9.JqCmG2WG6m4Ar1yAHHNc8PAf7ic5swDYM8NHiHLOvck"
}
```

<br>

### Endpoint ``/topicos``

**GET** : Lista todos os tópicos cadastrados. Não necessita estar cadastrado ou autenticado para enviar requisições GET para este Endpoint.
* Envio do modelo da consulta da requisição obrigatório:

```
{}
```

* Resposta da consulta:

```
{
  "totalElements": 2,
  "totalPages": 1,
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "first": true,
  "last": true,
  "size": 10,
  "content": [
    {
      "id": 2,
      "titulo": "Dúvida sobre Mysql",
      "mensagem": "Como consigo fazer um update em somente uma coluna da tabela?",
      "status": "NAO_RESPONDIDO",
      "curso": "Java",
      "data_criacao": "2024-07-11T18:59:12",
      "nome_autor": "Jorge Fontoura Neto",
      "contagem_respostas": 0,
      "solucao_resposta_id": null
    },
    {
      "id": 1,
      "titulo": "Duvida sobre o Spring Security",
      "mensagem": "Onde posso encontrar a documentação do spring security?",
      "status": "SOLUCIONADO",
      "curso": "Java",
      "data_criacao": "2024-07-11T12:04:28",
      "nome_autor": "José Guimarães Ferreira",
      "contagem_respostas": 1,
      "solucao_resposta_id": 1
    }
  ],
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "numberOfElements": 2,
  "empty": false
}
``` 

<br>

**POST** : O usuário precisa estar cadastrado e autenticado para cadastrar um novo tópico. O tópico não pode possuir titulo ou mensagem igual a qualquer outro tópico já cadastrado em todo o sistema.
* Envio do modelo da consulta da requisição:

```
{
  "titulo": "string",
  "mensagem": "string",
  "curso_id": 0
}
```

Onde: Todos os campos são obrigatórios.

<br>

### Endpoint ``/topicos/{id}``

Onde: ``{id}`` = id do tópico que deseja acessar

**GET** : Não necessita estar cadastrado ou autenticado para enviar requisições GET para este Endpoint.
* Não é necessário inserir Json no corpo do envio da requisição.
* Resposta da consulta com ``/topicos/1``:

```
{
  "id": 1,
  "titulo": "Duvida sobre o Spring Security",
  "mensagem": "Onde posso encontrar a documentação do spring security?",
  "status": "SOLUCIONADO",
  "curso": "Java",
  "data_criacao": "2024-07-11T12:04:28",
  "nome_autor": "José Guimarães Ferreira",
  "respostas": [
    {
      "id": 1,
      "mensagem": "esse é um teste de mensagem da resposta 1 e o novo autor",
      "solucao": "teste da solução aqui também pra ficar 1 daora o bgl novo autor",
      "autor": "Sou o root",
      "data_criacao": "2024-07-11T12:08:35",
      "topico_id": 1
    }
  ],
  "solucao_resposta_id": 1
}
``` 

<br>

**PUT** : O usuário autenticado necessita ter o perfil de administrador ou ser o próprio autor do tópico para que possa realizar a atualização.
* Envio do modelo da consulta da requisição:

```
{
  "titulo": "string",
  "mensagem": "string",
  "status": "string" Enum = [ "NAO_RESPONDIDO", "RESPONDIDO", "SOLUCIONADO" ],
  "curso_id": 0,
  "solucao_resposta_id": 0
}
```

Onde: Nenhum dos campos é obrigatório. O status deve ser preenchido com uma das opções do Enum = [ "NAO_RESPONDIDO" ou "RESPONDIDO" ou "SOLUCIONADO" ], caso "SOLUCIONADO", deve-se, obrigatoriamente, indicar o id da resposta que sustenta a solução do tópico.

<br>

**DELETE** : O usuário autenticado necessita ter o perfil de administrador ou ser o próprio autor do tópico para que possa realizar a exclusão do mesmo.
* Não é necessário inserir Json no corpo do envio da requisição.

<br>

### Endpoint ``/usuarios/{usuario_id}``

Onde: ``{usuario_id}`` = id do usuário que deseja acessar.

**PATCH** : Atualiza o perfil de um determinado usuário. O usuário autenticado necessita ter o perfil de administrador para que possa adicionar perfis de outros usuários.
* Envio do modelo da consulta da requisição:

```
{
  "perfil_id": [
    0
  ]
}
```

Onde: Todos os campos são obrigatórios.

<br>

### Endpoint ``/topicos/{topico_id}/respostas``

Onde: ``{topico_id}`` = id do tópico que deseja acessar as respostas.

**GET** : Lista todas as respostas do tópico selecionado. Não necessita estar cadastrado ou autenticado para enviar requisições GET para este Endpoint

* Envio do modelo da consulta da requisição obrigatório:

```
{}
```

* Resposta da consulta com ``/topicos/1/respostas``:

```
{
  "totalElements": 1,
  "totalPages": 1,
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "first": true,
  "last": true,
  "size": 10,
  "content": [
    {
      "id": 1,
      "mensagem": "esse é um teste de mensagem da resposta 1 e o novo autor",
      "solucao": "teste da solução aqui também pra ficar 1 daora o bgl novo autor",
      "autor": "Sou o root",
      "data_criacao": "2024-07-11T12:08:35",
      "topico_id": 1
    }
  ],
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "numberOfElements": 1,
  "empty": false
}
```

<br>

**POST** : Cadastra uma nova resposta para o tópico selecionado. O usuário precisa estar cadastrado e autenticado para que possa cadastrar uma nova resposta para o tópico.
* Envio do modelo da consulta da requisição:

```
{
  "mensagem": "string",
  "solucao": "string"
}
```

Onde: Todos os campos são obrigatórios.

<br>

### Endpoint ``/topicos/{topico_id}/respostas/{resposta_id}``

Onde: ``{topico_id}`` = id do tópico que deseja acessar as respostas.

Onde: ``{resposta_id}`` = id da resposta do tópico que deseja detalhar.

**GET** : Detalha a resposta selecionado do tópico. Não necessita estar cadastrado ou autenticado para enviar requisições GET para este Endpoint
* Não é necessário inserir Json no corpo do envio da requisição.
* Resposta da consulta com ``/topicos/1/respostas/1``:

```
{
  "id": 1,
  "mensagem": "esse é um teste de mensagem da resposta 1 e o novo autor",
  "solucao": "teste da solução aqui também pra ficar 1 daora o bgl novo autor",
  "autor": "Sou o root",
  "data_criacao": "2024-07-11T12:08:35",
  "topico_id": 1
}
```

<br>

**DELETE** : O usuário autenticado necessita ter o perfil de administrador ou ser o próprio autor da resposta para que possa realizar a exclusão da mesma.
* Não é necessário inserir Json no corpo do envio da requisição.

<br>

### Endpoint ``/perfil``

**POST** : Cadastra novos perfis ao sistema. O usuário autenticado necessita ter o perfil de administrador para cadastrar novos perfis.
* Envio do modelo da consulta da requisição:

```
{
  "nome": "string"
}
```

Onde: Todos os campos são obrigatórios.

<br>

### Endpoint ``/curso``

**POST** : Cadastra um novo curso no sistema. O usuário precisa estar cadastrado e autenticado para cadastrar um novo curso. O curso não pode possuir nome igual a qualquer outro curso já cadastrado no sistema.

* Envio do modelo do corpo da requisição para envio obrigatório do Json para cadastro de novo tópico com o método de requisição POST:

```
{
  "nome": "string",
  "categoria": "string"
}
```

Onde: Todos os campos são obrigatórios.

<br>
