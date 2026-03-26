package com.phonebook.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Утилитный класс для чтения параметров из файла data.properties.
 *
 * Зачем нужен: чтобы не хардкодить URL, пути и пароли прямо в тестах.
 * Все настройки хранятся в одном месте — в файле data.properties.
 * Если URL изменится — меняем только в properties, а не в каждом тесте.
 */
public class PropertiesLoader {

    // Путь к файлу конфигурации.
    // "/" в начале означает: искать от корня classpath (папки resources)
    private static final String PROP_FILE = "/data.properties";

    // Приватный конструктор запрещает создавать объекты этого класса:
    // PropertiesLoader loader = new PropertiesLoader(); — так нельзя
    // Все методы вызываются напрямую: PropertiesLoader.loadProperty("url")
    private PropertiesLoader() {
    }

    /**
     * Читает значение из data.properties по ключу.
     *
     * Пример: loadProperty("url") → "https://contactapp-telran-backend.herokuapp.com"
     * Пример: loadProperty("valid.email") → "user@example.com"
     *
     * @param name  ключ из файла data.properties (например "url", "valid.email")
     * @return      значение по этому ключу, или пустая строка если ключ null
     * @throws RuntimeException если файл data.properties не найден или не читается
     */
    public static String loadProperty(String name) {

        // Создаём пустой контейнер для хранения пар ключ=значение из файла
        Properties properties = new Properties();

        try {
            // Находим файл data.properties в ресурсах проекта и загружаем его содержимое
            // getResourceAsStream — открывает файл как поток байт для чтения
            properties.load(PropertiesLoader.class.getResourceAsStream(PROP_FILE));

        } catch (IOException e) {
            // Если файл не найден или недоступен — бросаем ошибку и останавливаем тест
            throw new RuntimeException("Не удалось загрузить файл: " + PROP_FILE, e);
        }

        // Начальное значение — пустая строка (на случай если ключ не найден)
        String value = "";

        if (name != null) {
            // Ищем значение по ключу в загруженных свойствах
            // Например: name = "url" → value = "https://contactapp-telran-backend.herokuapp.com"
            value = properties.getProperty(name);
        }

        return value;
    }
}