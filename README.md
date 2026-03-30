# 📱 QA76m Phonebook API Automation

Автоматизация тестирования Phonebook API с использованием OkHttp и TestNG.


---

## 📌 Lesson 35 — Initial Setup

Базовая настройка проекта:

- подключение OkHttp (HTTP client)
- настройка TestNG
- использование Gson для сериализации/десериализации JSON
- работа с properties (data.properties)
- реализация логина и получение токена
- базовая структура проекта (core, dto, tests)

---

## 📌 Lesson 36 — CRUD API Tests (OkHttp)

Реализованы основные API тесты:

- Login (авторизация)
- Add Contact
- Get All Contacts
- Delete Contact

Используется OkHttp для отправки HTTP-запросов.

---

## 📌 HW36 — Negative API Tests

Реализованы негативные тесты:

- получение всех контактов без авторизации → 403 Forbidden
- добавление контакта без обязательного поля lastName → 400 Bad Request

---

## 🛠️ Tech Stack

- Java
- OkHttp
- TestNG
- Gson

---

## 📂 Project Structure
