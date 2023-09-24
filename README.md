# Intro

This is a project to test equivalent functionalities in Spring Boot Webflux + Spring Boot R2DBC stack against Spring Boot Web + Spring Boot JPA stack.

# Pre-requisite

- JDK 17+
- Docker and Docker Compose installed

# TL;DR

Start a MySQL database.

```bash
docker compose up -d
```

Set up a base64 encoded secret to `jwt.secret-key` properties in application properties.

If you do not have Maven installed, run following command to install Maven.

Run this if you are using Unix-like OS.

```bash
mvnw
```

Run this if you are using Windows.

```cmd
./mvnw.cmd
```

Run the application.

```bash
mvn spring-boot:run
```

# Project Details

## Security Spec

This project is using jwt in `Authorization` header with `Bearer` scheme to authenticate.

Jwt is signed with base64 encoded secret using HS256 algorithm with following claims required in payload.

- `userId`: an id of user as integer
- `username`: a username as string

The base64 encoded secret is set to `jwt.secret-key` properties in application properties.

Here is a jwt for testing expired until 2050.

```plaintext
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.NCNUXdkc0uYwC0RzNWkMqBmzLkum8R617Q7IME3lmUo
```
