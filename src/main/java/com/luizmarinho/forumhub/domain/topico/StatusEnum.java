package com.luizmarinho.forumhub.domain.topico;

public enum StatusEnum {
    NAO_RESPONDIDO("Aguardando respostas..."),
    RESPONDIDO("Há possíveis soluções disponíveis"),
    SOLUCIONADO("Há uma solução definitiva para este tópico");

    private final String description;

    StatusEnum(String description) {
        this.description = description;
    }

}
