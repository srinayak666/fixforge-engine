# 🚀 FixForge Engine

Backend engine for **FixForge**, responsible for analyzing input data/logs and producing actionable insights or fixes.

---

## 📌 Overview

FixForge Engine is a lightweight, stateless backend service that:

- 🔍 Analyzes logs or input data
- ⚙️ Processes and detects issues
- 🤖 Generates insights or suggested fixes
- 📡 Exposes REST APIs for UI or integrations

> ⚠️ This service does NOT require a database by default.

---

## 🏗️ Tech Stack

- **Language**: Java
- **Framework**: Spring Boot
- **Build Tool**: Gradle
- **Architecture**: Layered (Controller → Service → Processor)
- **API**: RESTful services

---

## 📂 Project Structure

```
fixforge-engine/
│
├── src/main/java/
│   ├── controller/
│   ├── service/
│   ├── processor/
│   ├── model/
│   ├── dto/
│   └── FixforgeApplication.java
│
├── src/main/resources/
│   ├── application.yml
│
├── build.gradle
├── settings.gradle
└── README.md
```

---

## ⚙️ Prerequisites

- Java 17+
- Gradle (or use wrapper)

Verify:

```bash
java -version
./gradlew -v
```

---

## 🔧 Setup & Installation

```bash
git clone https://github.com/srinayak666/fixforge-engine.git
cd fixforge-engine
```

---

## ▶️ Running the Application

### Using Gradle Wrapper (recommended)

```bash
./gradlew bootRun
```

### Or build and run

```bash
./gradlew build
java -jar build/libs/fixforge-engine.jar
```

App runs at:

```
http://localhost:8080
```

---

## 🔗 API Endpoints

| Method | Endpoint     | Description                |
|--------|-------------|----------------------------|
| GET    | /health     | Health check               |
| POST   | /analyze    | Analyze input/log data     |
| GET    | /results    | Fetch processed results    |

---

## 🔐 Configuration

Example `application.yml`:

```yaml
server:
  port: 8080

spring:
  application:
    name: fixforge-engine
```

---

## 🧠 Core Flow

1. Client sends data via API
2. Controller receives request
3. Service processes logic
4. Processor analyzes data
5. Response returned

---

## 🧠 Key Features

- ✅ Stateless backend (no DB dependency)
- ✅ Clean architecture
- ✅ Lightweight and fast
- ✅ Easy integration with UI

---

## 🧪 Testing

```bash
./gradlew test
```

---

## 📦 Build

```bash
./gradlew build
```

---

## 🚀 Deployment

- Docker
- Cloud (AWS / Azure / GCP)
- Kubernetes (optional)

---

## 🐛 Common Issues

### Port already in use
Change port in `application.yml`

### Java version mismatch
Ensure Java 17+

---

## 🤝 Contributing

1. Fork repo  
2. Create branch  
3. Commit changes  
4. Open PR  

---

## 📄 License

MIT License

---

## 👨‍💻 Author

Srikanth Nayak
