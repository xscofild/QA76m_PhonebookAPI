package com.phonebook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO для отправки запроса аутентификации.
 * Содержит учётные данные пользователя (email и пароль).
 * Используется при POST-запросе на эндпоинт входа в систему.
 */
@Getter
@Setter
@ToString
@Builder
public class AuthRequestDto {

    /** Email пользователя (используется как username). */
    private String username;

    /** Пароль пользователя. */
    private String password;
}