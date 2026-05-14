# 📱 QA76m_PhonebookAPI

API automation framework for the **Phonebook REST API**, built with **Java 17**, **OkHttp**, and **TestNG**.

> Course project from AIT QA Cohort 76M — extended with DTO architecture, properties-based config, and negative test coverage.

---

## 🎯 What's Covered

| Endpoint | Tests | Method |
|---|---|---|
| `POST /v1/user/login/usernamepassword` | Login success, wrong password, non-existent email, JWT format validation | OkHttp + Gson |
| `GET /v1/contacts` | Get all contacts (authenticated) | OkHttp + Bearer token |
| `POST /v1/contacts` | Add contact (positive) | DTO + builder pattern |
| `DELETE /v1/contacts/{id}` | Delete contact by ID | OkHttp |

---

## 🧰 Tech Stack

- **Java 17**
- **OkHttp 5.3.2** — HTTP client
- **Gson 2.13.2** — JSON serialization/deserialization
- **TestNG 7.12.0** — test runner with `Assert` + `SoftAssert`
- **Lombok** — DTO boilerplate reduction (`@Builder`, `@Getter`, `@Setter`)
- **Gradle** — build & dependency management
- **SLF4J** — logging

---

## 🏗️ Project Structure

```
src/
├── main/java/com/phonebook/
│   ├── dto/                    # Request/Response models
│   │   ├── AuthRequestDto.java
│   │   ├── ContactDto.java     (@Builder)
│   │   ├── TokenDto.java
│   │   ├── ErrorDto.java
│   │   ├── MessageDto.java
│   │   └── AllContactsDto.java
│   └── utils/
│       └── PropertiesLoader.java
└── test/
    ├── java/com/phonebook/
    │   ├── core/TestBase.java          # Shared OkHttpClient, Gson, SoftAssert
    │   └── okhttpTests/                 # Endpoint test classes
    └── resources/
        └── data.properties              # baseUrl, paths, credentials (placeholders)
```

---

## 🔑 Key Patterns

- **DTO + Builder** for request payloads:
  ```java
  ContactDto contact = ContactDto.builder()
      .name("John").lastName("Doe")
      .email("john@example.com")
      .build();
  ```
- **PropertiesLoader** keeps URLs, paths, and credentials out of code
- **Bearer token** authentication via `Authorization` header
- **Negative scenarios**: wrong password, non-existent email, JWT format validation
- **SoftAssert** for multi-field response validation (status + error message + error type)

---

## 🚀 How to Run

### Prerequisites
- Java 17+
- Gradle (or use included wrapper)

### Setup credentials
Create `src/test/resources/data.properties` (not committed):
```properties
url=https://contactapp-telran-backend.herokuapp.com
valid.email=your@email.com
valid.password=YourPassword
token=your-jwt-token
log.in=/v1/user/login/usernamepassword
contact.controller=/v1/contacts
auth=Authorization
```

### Run all tests
```bash
./gradlew test
```

### Run a specific test class
```bash
./gradlew test --tests com.phonebook.okhttpTests.LoginTests
```

---

## 📝 Notes

- **JWT token** is loaded from properties for endpoints requiring authentication. For production-grade tests, the login flow should generate the token dynamically per test run.
- Backend API is the **Telran Phonebook training API** — a Heroku-hosted Spring Boot service used in QA training programs.
- This project intentionally uses **OkHttp directly** (not REST Assured) to demonstrate raw HTTP request/response handling.

---

## 🎓 Author

**Serdar Kerimov** — [github.com/xscofild](https://github.com/xscofild) · [LinkedIn](https://www.linkedin.com/in/serdarkerimov/)
QA Engineer | Java · Selenium · REST Assured · SQL
