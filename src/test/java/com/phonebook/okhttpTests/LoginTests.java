package com.phonebook.okhttpTests;

import com.phonebook.core.TestBase;
import com.phonebook.dto.AuthRequestDto;
import com.phonebook.dto.ErrorDto;
import com.phonebook.dto.TokenDto;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Тесты аутентификации.
 * Эндпоинт: POST /v1/user/login/usernamepassword
 */
public class LoginTests extends TestBase {

    // ==================================================================================
    // ТЕСТ 1: Успешный вход с правильными email и паролем
    // Ожидаем: сервер вернёт HTTP 200 OK
    // ==================================================================================
    @Test
    public void loginSuccessTest() throws IOException {

        // ШАГ 1 — Подготовка тела запроса (что мы отправим на сервер)
        // Создаём объект AuthRequestDto через builder — это как заполнить форму входа:
        // вводим email и пароль, которые хранятся в data.properties
        AuthRequestDto authRequest = AuthRequestDto.builder()
                .username(email)       // валидный email из data.properties
                .password(password)    // валидный пароль из data.properties
                .build();              // "собираем" объект — он готов

        // ШАГ 2 — Конвертируем объект в JSON и упаковываем в тело HTTP-запроса
        // gson.toJson(authRequest) → превращает объект в строку: {"username":"...","password":"..."}
        // RequestBody.create(...)  → упаковывает эту строку как тело POST-запроса
        RequestBody requestBody = RequestBody.create(gson.toJson(authRequest), JSON);

        // ШАГ 3 — Строим сам HTTP-запрос
        // Указываем: куда идём (URL) и что несём (requestBody)
        Request request = new Request.Builder()
                .url(baseUrl + loginPath) // итоговый URL: https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword
                .post(requestBody)        // метод POST + тело запроса
                .build();                 // запрос готов, но ещё не отправлен

        // ШАГ 4 — Отправляем запрос и получаем ответ от сервера
        // client.newCall(request) → создаём "звонок" (запрос готов к отправке)
        // .execute()              → отправляем и ждём ответа (синхронно)
        // response                → содержит статус-код, заголовки и тело ответа
        Response response = client.newCall(request).execute();

        // ШАГ 5 — Проверяем статус-код ответа
        // assertEquals(200) точнее чем isSuccessful() — проверяет именно 200, а не весь диапазон 200–299
        Assert.assertEquals(response.code(), 200,
                "Ожидался статус 200. Фактический статус: " + response.code());

        // ШАГ 6 — Читаем тело ответа и десериализуем JSON в объект TokenDto
        // response.body().string() → {"token":"eyJhbGciOi..."}
        // gson.fromJson(...)       → превращает JSON-строку обратно в Java-объект
        TokenDto tokenDto = gson.fromJson(response.body().string(), TokenDto.class);

        // ШАГ 7 — Проверяем, что токен реально пришёл (не null и не пустой)
        Assert.assertNotNull(tokenDto.getToken(), "Токен отсутствует в ответе");

        System.out.println("Полученный токен: " + tokenDto.getToken());
    }

    // ==================================================================================
    // ТЕСТ 2: Вход с неправильным паролем
    // Ожидаем: сервер вернёт HTTP 401 Unauthorized (не авторизован)
    // ==================================================================================
    @Test
    public void loginWithWrongPasswordTest() throws IOException {

        // ШАГ 1 — Готовим запрос: email правильный, пароль — неправильный
        AuthRequestDto authRequest = AuthRequestDto.builder()
                .username(email)              // валидный email
                .password("wrongPassword!")   // НЕВЕРНЫЙ пароль — специально для негативного теста
                .build();

        // ШАГ 2 — Конвертируем в JSON и упаковываем в тело запроса
        RequestBody requestBody = RequestBody.create(gson.toJson(authRequest), JSON);

        // ШАГ 3 — Строим HTTP-запрос (тот же эндпоинт, что и для успешного входа)
        Request request = new Request.Builder()
                .url(baseUrl + loginPath)
                .post(requestBody)
                .build();

        // ШАГ 4 — Отправляем запрос
        Response response = client.newCall(request).execute();

        // ШАГ 5 — SoftAssert проверяет статус-код, но НЕ останавливает тест при провале
        // В отличие от Assert — собирает ВСЕ ошибки и показывает их разом в конце через assertAll()
        softAssert.assertEquals(response.code(), 401,
                "Ожидался статус 401 при неверном пароле");

        // ШАГ 6 — Десериализуем тело ответа в ErrorDto
        // Сервер при ошибке возвращает JSON с полями: error (тип) и message (описание)
        // response.body().string() → {"error":"Unauthorized","message":"Login or Password incorrect"}
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        // ШАГ 7 — Проверяем поле "error" в теле ответа
        softAssert.assertEquals(errorDto.getError(), "Unauthorized",
                "Поле 'error' некорректно");

        // ШАГ 8 — Проверяем поле "message" в теле ответа
        softAssert.assertEquals(errorDto.getMessage(), "Login or Password incorrect",
                "Поле 'message' некорректно");

        // ШАГ 9 — assertAll() запускает все отложенные проверки и выводит все ошибки сразу
        softAssert.assertAll();
    }

    // ==================================================================================
    // ТЕСТ 3: Вход с несуществующим email
    // Ожидаем: сервер вернёт HTTP 401 Unauthorized
    // ==================================================================================
    @Test
    public void loginWithNonExistentEmailTest() throws IOException {

        // ШАГ 1 — Готовим запрос: email несуществующий, пароль правильный
        AuthRequestDto authRequest = AuthRequestDto.builder()
                .username("nonexistent@test.com") // НЕСУЩЕСТВУЮЩИЙ email — специально для негативного теста
                .password(password)               // валидный пароль
                .build();

        // ШАГ 2 — Конвертируем в JSON и упаковываем в тело запроса
        RequestBody requestBody = RequestBody.create(gson.toJson(authRequest), JSON);

        // ШАГ 3 — Строим HTTP-запрос
        Request request = new Request.Builder()
                .url(baseUrl + loginPath)
                .post(requestBody)
                .build();

        // ШАГ 4 — Отправляем запрос
        Response response = client.newCall(request).execute();

        // ШАГ 5 — Проверяем, что сервер вернул 401
        Assert.assertEquals(response.code(), 401,
                "При несуществующем email ожидался статус 401 Unauthorized");
    }

    // ==================================================================================
    // ТЕСТ 4: Проверка формата JWT-токена при успешном входе
    // Ожидаем: токен не пустой и состоит из 3 частей, разделённых точкой
    // ==================================================================================
    @Test
    public void loginTokenFormatTest() throws IOException {

        // ШАГ 1 — Готовим запрос с правильными данными (хотим получить токен)
        AuthRequestDto authRequest = AuthRequestDto.builder()
                .username(email)
                .password(password)
                .build();

        // ШАГ 2 — Конвертируем в JSON и упаковываем в тело запроса
        RequestBody requestBody = RequestBody.create(gson.toJson(authRequest), JSON);

        // ШАГ 3 — Строим HTTP-запрос
        Request request = new Request.Builder()
                .url(baseUrl + loginPath)
                .post(requestBody)
                .build();

        // ШАГ 4 — Отправляем запрос
        Response response = client.newCall(request).execute();

        // ШАГ 5 — Проверяем, что вход прошёл успешно (иначе токена не будет вовсе)
        Assert.assertTrue(response.isSuccessful(),
                "Вход должен быть успешным перед проверкой токена");

        // ШАГ 6 — Читаем тело ответа как строку (это JSON с токеном)
        // response.body().string() → {"token":"eyJhbGci..."}
        String responseBody = response.body().string();

        // ШАГ 7 — Десериализуем JSON-строку обратно в Java-объект TokenDto
        // gson.fromJson(...) → обратная операция: строка → объект
        TokenDto tokenDto = gson.fromJson(responseBody, TokenDto.class);

        // ШАГ 8 — Проверяем, что токен существует и не пустой
        Assert.assertNotNull(tokenDto.getToken(), "Токен не должен быть null");
        Assert.assertFalse(tokenDto.getToken().isEmpty(), "Токен не должен быть пустым");

        // ШАГ 9 — Проверяем формат JWT-токена
        // JWT всегда выглядит так: xxxxx.yyyyy.zzzzz (три части через точку)
        // split("\\.") → разбиваем строку по точке на массив частей
        String[] tokenParts = tokenDto.getToken().split("\\.");
        Assert.assertEquals(tokenParts.length, 3,
                "JWT-токен должен состоять из 3 частей, разделённых точкой (header.payload.signature)");
    }
}