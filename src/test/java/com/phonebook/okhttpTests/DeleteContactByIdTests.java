package com.phonebook.okhttpTests;

import com.phonebook.core.TestBase;
import com.phonebook.dto.ContactDto;
import com.phonebook.dto.MessageDto;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeleteContactByIdTests extends TestBase {

    String id;

    @BeforeMethod
    public void precondition() throws Exception {
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

        MessageDto messageDto = gson.fromJson(responseBody, MessageDto.class);
        String[] split = messageDto.getMessage().split(": ");
        id = split[1];
    }

    @Test
    public void deleteContactByIdSuccessTest() throws Exception {
        Request request = new Request.Builder()
                .url(baseUrl + contactsPath + "/" + id)
                .addHeader(auth, "Bearer " + token)
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        // Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        MessageDto messageDto = gson.fromJson(response.body().string(), MessageDto.class);

        Assert.assertEquals(messageDto.getMessage(), "Contact was deleted!");

    }
}
