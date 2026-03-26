package com.phonebook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO для десериализации тела ответа при ошибке (4xx / 5xx).
 * Сервер возвращает этот объект, когда запрос не может быть выполнен.
 * Используется для проверки кода ошибки и сообщения в негативных тестах.
 */
@Getter
@Setter
@ToString
@Builder
public class ErrorDto {

    /** HTTP-код статуса ответа (например, 400, 401, 404). */
    private int status;

    /** Краткое название HTTP-ошибки (например, "Unauthorized", "Bad Request"). */
    private String error;

    /** Подробное сообщение об ошибке от сервера. */
    private String message;

    /** Путь эндпоинта, на котором произошла ошибка. */
    private String path;
}