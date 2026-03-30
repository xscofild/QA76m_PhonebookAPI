package com.phonebook.core;

import com.google.gson.Gson;
import com.phonebook.utils.PropertiesLoader;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.testng.asserts.SoftAssert;

/**
 * Базовый класс для всех тестов.
 * Содержит общие инструменты и конфигурационные данные,
 * которые наследуются во всех тестовых классах проекта.
 */
public class TestBase {

    /** Gson — для сериализации Java-объектов в JSON и обратно. */
    protected Gson gson = new Gson();

    /** OkHttpClient — HTTP-клиент для выполнения запросов к API. */
    protected OkHttpClient client = new OkHttpClient();

    /// SoftAssert — для выполнения мягких утверждений, позволяя тесту продолжать выполнение после неудачных проверок.
    protected SoftAssert softAssert = new SoftAssert();

    /** Content-Type заголовок для JSON-запросов. */
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /** Базовый URL API, загружается из data.properties (ключ: "url"). */
    public static final String baseUrl = PropertiesLoader.loadProperty("url");

    /** Путь эндпоинта для входа в систему (ключ: "log.in"). */
    public static final String loginPath = PropertiesLoader.loadProperty("log.in");

    /** Путь эндпоинта для работы с контактами (ключ: "contact.controller"). */
    public static final String contactsPath = PropertiesLoader.loadProperty("contact.controller");

    /** Валидный email пользователя для позитивных тестов (ключ: "valid.email"). */
    public static final String email = PropertiesLoader.loadProperty("valid.email");

    /** Валидный пароль пользователя для позитивных тестов (ключ: "valid.password"). */
    public static final String password = PropertiesLoader.loadProperty("valid.password");

    /** Токен авторизации, полученный после успешного входа, для использования в защищённых эндпоинтах. */
    public static final String token = PropertiesLoader.loadProperty("token");

    // Эндпоинт для получения информации о текущем пользователе (ключ: "auth").
    public static final String auth = PropertiesLoader.loadProperty("auth");


}