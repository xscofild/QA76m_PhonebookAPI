package com.phonebook.okhttpTests;

import com.phonebook.core.TestBase;
import com.phonebook.dto.ContactDto;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Homework 36
 *
 * Негативные тесты:
 * 1. Получение всех контактов без авторизации — ожидаем 403 Forbidden
 * 2. Добавление контакта без обязательного поля lastName — ожидаем 400 Bad Request
 */
public class Homework36 extends TestBase {

    @Test
    public void getAllContactsWithoutAuthTest() throws IOException {

        Request request = new Request.Builder()
                .url(baseUrl + contactsPath)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        Assert.assertEquals(response.code(), 403,
                "Ожидался статус 403, фактический: " + response.code());
    }

    @Test
    public void addContactWithoutLastNameTest() throws IOException {

        ContactDto contactDto = ContactDto.builder()
                .name("John")
                .lastName("")
                .email("johndoe@example.com")
                .phone("1234567890")
                .address("123 Main St")
                .description("Test contact")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto), JSON);

        Request request = new Request.Builder()
                .url(baseUrl + contactsPath)
                .addHeader(auth, "Bearer " + token)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        Assert.assertEquals(response.code(), 400);
        Assert.assertTrue(responseBody.contains("lastName"));
    }
}