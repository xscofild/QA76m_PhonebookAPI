package com.phonebook.okhttpTests;

import com.phonebook.core.TestBase;
import com.phonebook.dto.AllContactsDto;
import com.phonebook.dto.ContactDto;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Тесты получения списка контактов.
 * Эндпоинт: GET /v1/contacts
 */
public class GetAllContactsTests extends TestBase {

    // ==================================================================================
    // ТЕСТ 1: Успешное получение списка контактов авторизованным пользователем
    // Ожидаем: сервер вернёт HTTP 200 OK + список контактов
    // ==================================================================================
    @Test
    public void getAllContactsSuccessTest() throws IOException {

        // ШАГ 1 — Строим HTTP GET-запрос
        // В отличие от POST-запросов — тела нет, только заголовки и URL
        // addHeader("Authorization", ...) → передаём токен, чтобы сервер знал кто мы
        // "Bearer " + token → стандартный формат: слово Bearer + пробел + сам JWT-токен
        Request request = new Request.Builder()
                .url(baseUrl + contactsPath)                    // URL эндпоинта: .../v1/contacts
                .addHeader("Authorization", "Bearer " + token)  // заголовок авторизации
                .get()                                          // метод GET — запрашиваем данные
                .build();                                       // запрос готов, но ещё не отправлен

        // ШАГ 2 — Отправляем запрос и получаем ответ от сервера
        Response response = client.newCall(request).execute();

        // ШАГ 3 — Читаем тело ответа в переменную ДО всех проверок
        // response.body().string() можно вызвать только ОДИН РАЗ — после тело закрывается
        String responseBody = response.body().string();

        // ШАГ 4 — Выводим в консоль что вернул сервер
        System.out.println("Статус-код: " + response.code());
        System.out.println("Тело ответа: " + responseBody);

        // ШАГ 5 — Проверяем, что сервер вернул успешный статус-код
        Assert.assertTrue(response.isSuccessful(),
                "Ожидался успешный ответ. Фактический статус: " + response.code());

        // ШАГ 6 — Десериализуем JSON в объект AllContactsDto
        // AllContactsDto содержит список контактов: { "contacts": [ {...}, {...} ] }
        // gson.fromJson(...) → превращает JSON-строку в Java-объект
        AllContactsDto contactsDto = gson.fromJson(responseBody, AllContactsDto.class);

        // ШАГ 7 — Перебираем список контактов и выводим каждый в консоль
        // for-each → проходим по каждому ContactDto из списка
        for (ContactDto contactDto : contactsDto.getContacts()) {
            System.out.println("ID: " + contactDto.getId());
            System.out.println("Имя: " + contactDto.getName());
        }
    }
}