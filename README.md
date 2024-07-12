# API Rest do Forum Hub da Alura

API para o back-end do fórum hub.

## Endpoints

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

* Envio do modelo do corpo da requisição para envio obrigatório do Json para cadastro de novo tópico com o método de requisição POST:

```
{
  "titulo": "string",
  "mensagem": "string",
  "curso_id": 0
}
```

Onde: ``titulo``, ``mensagem`` e ``curso_id`` são obrigatórios.

<br>

### Endpoint ``/topicos/{id}``

Onde: ``{id}`` = id do tópico que deseja acessar

**GET** : Não necessita estar cadastrado ou autenticado para enviar requisições GET para este Endpoint.
* Não é necessário inserir Json no corpo do envio da requisição.
* Resposta da consulta:

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

### Endpoint ``/usuarios``

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

**PUT** : O usuário precisa estar cadastrado e autenticado para que possa detalhar o próprio perfil. Somente quem pode adicionar novos perfis ao próprio usuário é o usuário com perfil de administrador
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

