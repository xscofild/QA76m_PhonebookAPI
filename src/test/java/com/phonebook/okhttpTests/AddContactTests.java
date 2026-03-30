package com.phonebook.okhttpTests;

import com.phonebook.core.TestBase;
import com.phonebook.dto.ContactDto;
import com.phonebook.dto.MessageDto;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddContactTests extends TestBase {

    @Test
    public void addContactSuccessTest() throws IOException {

        ContactDto contactDto = ContactDto.builder()
                .name("John")
                .lastName("Doe")
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
        System.out.println("Статус-код: " + response.code());
        System.out.println("Тело ответа: " + responseBody);

        Assert.assertTrue(response.isSuccessful());

        MessageDto messageDto = gson.fromJson(responseBody, MessageDto.class);
        System.out.println("Ответ сервера: " + messageDto.getMessage());
    }

}
