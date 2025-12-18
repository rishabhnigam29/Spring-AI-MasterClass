# Spring-AI-MasterClass

## Overview

**Spring-AI-MasterClass** is a lightweight Spring Boot demonstration project showcasing how to integrate **multiple AI providers** using **Spring AI**. The goal of this repository is to provide clear, configuration-driven examples of common AI capabilities that modern Java backends need.

This project is intentionally kept simple and focused so it can be used as:
- A learning reference for Spring AI
- A playground for experimenting with different AI providers
- A starter template for AI-enabled Spring Boot applications

---

## Supported AI Capabilities

- **Chat / Text generation**
  - OpenAI
  - Ollama (local / on-prem models)
- **Image generation**
  - DALL·E (OpenAI)
- **Embeddings**
  - Provider selection via configuration (e.g. Ollama, OpenAI)
- **Moderation**
  - Content moderation via configured provider(s)

All integrations are managed using **Spring AI abstractions**, making it easy to switch providers with minimal code changes.

---

## Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring AI**
- **Maven**
- AI Providers:
  - OpenAI
  - Ollama
  - Stability AI
  - Mistral AI

---

## Prerequisites

Before running the application, ensure you have:

- Java JDK 17 or higher
- Maven 3.6+
- API keys for the providers you plan to use
- (Optional) A running **Ollama** instance for local models

---

## Configuration

Primary configuration is defined in:

```
src/main/resources/application.properties
```

### Environment Variables

Secrets such as API keys **must not be committed** and should be provided via environment variables or a secure secrets store.

Example (PowerShell):

```powershell
$env:SPRING_AI_OPENAI_API_KEY = "sk-..."
$env:SPRING_AI_STABILITYAI_API_KEY = "sk-..."
$env:SPRING_AI_MISTRALAI_API_KEY = "sk-..."
```

### Key Configuration Options

- `spring.ai.openai.api-key` – OpenAI API key
- `spring.ai.ollama.base-url` – Ollama server URL (e.g. `http://localhost:11434`)
- `spring.ai.model.embedding` – Select embedding provider (`ollama`, `openai`, etc.)
- `logging.level.org.springframework.ai=DEBUG` – Enable Spring AI debug logs

Configuration is **provider-agnostic**, allowing you to switch models without changing application code.

---

## Build & Run

### Run with Maven

```bash
mvn spring-boot:run
```

### Build and Run JAR

```bash
mvn -DskipTests package
java -jar target/*.jar
```

### Run Tests

```bash
mvn test
```

---

## Example API Usage

> Actual endpoints may vary depending on controller implementations. Adjust paths as needed.

### Chat

```bash
curl -X POST "http://localhost:8080/api/chat" \
  -H "Content-Type: application/json" \
  -d '{"message":"Hello"}'
```

### Image Generation

```bash
curl -X POST "http://localhost:8080/api/image" \
  -H "Content-Type: application/json" \
  -d '{"prompt":"A futuristic city"}'
```

### Embeddings

```bash
curl -X POST "http://localhost:8080/api/embed" \
  -H "Content-Type: application/json" \
  -d '{"text":"sample text"}'
```

### Moderation

```bash
curl -X POST "http://localhost:8080/api/moderation" \
  -H "Content-Type: application/json" \
  -d '{"input":"some user content"}'
```

---

## Troubleshooting

- **Invalid API key errors**: Verify environment variables and provider quotas.
- **Ollama connection issues**: Ensure Ollama is running and `spring.ai.ollama.base-url` is correct.
- **Model not found**: Confirm model names are supported by the selected provider.
- Enable debug logging with:

```properties
logging.level.org.springframework.ai=DEBUG
```

---

## Learning Resources

If you want a structured, in-depth walkthrough of building AI-powered Java applications using **Spring AI**, this repository aligns closely with the following course:

- **Udemy Course:** *Spring AI – Build Java AI Apps, Chatbots & RAG Systems (2026)*  
  https://www.udemy.com/course/spring-ai-build-java-ai-apps-chatbots-rag-systems-2026/?referralCode=F89549F5C0391866566B

Topics covered include:
- Multi-provider Spring AI integrations
- Chat, embeddings, image generation, and moderation
- Configuration-driven AI architectures
- Real-world backend patterns

---

## License

See the `LICENSE` file in the repository. If none is present, add one before distributing or reusing this code.

---

## Maintainer

- **GitHub:** `rishab
