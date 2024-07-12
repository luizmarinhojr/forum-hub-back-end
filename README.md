# API Rest do Forum Hub da Alura

API para o back-end do fórum hub.

## Endpoints

### Endpoint ``/topicos``

**GET** : Lista todos os tópicos cadastrados. Não necessita estar cadastrado ou autenticado para enviar requisições GET para este Endpoint.

* Envio do modelo da consulta da requisição obrigatório:

```
{
  "page": 0,
  "size": 10
}
```

* Recebimento da consulta:

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

**POST** : O usuário precisa estar cadastrado e autenticado para cadastrar um novo tópico.

* Envio do modelo do corpo da requisição para envio obrigatório do Json para cadastro de novo tópico com o método de requisição POST:

```
{
  "titulo": "string",
  "mensagem": "string",
  "curso_id": 0
}
```

### Endpoint ``/topicos/{id}``

Onde:

``{id}`` = id do tópico que deseja detalhar

**GET** : Não necessita estar cadastrado ou autenticado para enviar requisições GET para este Endpoint.
* Não é necessário inserir Json no corpo da requisição.
* Recebimento da consulta:

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
