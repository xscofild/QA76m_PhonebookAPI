package com.phonebook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO для получения токена аутентификации из ответа сервера.
 * Возвращается сервером при успешном входе в систему.
 * Токен используется в заголовке Authorization для последующих запросов.
 */
@Getter
@Setter
@ToString
@Builder
public class TokenDto {

    /** JWT-токен, выданный сервером после успешной аутентификации. */
    private String token;
}