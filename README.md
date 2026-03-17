# 🚀 FixForge Engine

Backend engine for **FixForge**, responsible for analyzing input data/logs and producing actionable insights or fixes.

---

## 📌 Overview

FixForge Engine is a lightweight, stateless backend service that:

- 🔍 Analyzes logs or input data
- ⚙️ Processes and detects issues
- 🤖 Generates insights or suggested fixes
- 📡 Exposes REST APIs for UI or integrations

> ⚠️ This service uses **application.properties** (not YAML) and **Gradle** (not Maven).
> ⚠️ No database dependency by default.

---

## 🏗️ Tech Stack

- **Language**: Java
- **Framework**: Spring Boot
- **Build Tool**: Gradle
- **Configuration**: application.properties
- **Architecture**: Layered (Controller → Service → Processor)
- **API**: RESTful services

---

## 📂 Detailed Project Structure & File Responsibilities

```
fixforge-engine/
│
├── src/main/java/
│   ├── controller/        # Handles HTTP requests & responses
│   ├── service/           # Business logic orchestration
│   ├── processor/         # Core analysis engine logic
│   ├── model/             # Internal data models
│   ├── dto/               # API request/response objects
│   └── FixforgeApplication.java   # Spring Boot entry point
│
├── src/main/resources/
│   ├── application.properties     # App configuration (port, app name)
│
├── build.gradle           # Dependency management & build config
├── settings.gradle        # Project settings
└── README.md
```

---

## 🧠 File-Level Explanation

### 🔹 Controller Layer (`controller/`)
- Accepts incoming API requests
- Validates input
- Calls service layer
- Returns response

### 🔹 Service Layer (`service/`)
- Coordinates business logic
- Calls processor layer
- Handles transformations between DTO and models

### 🔹 Processor Layer (`processor/`)
- Core engine of FixForge
- Performs analysis / processing logic
- Produces results based on input data

### 🔹 DTO (`dto/`)
- Defines request/response structures
- Used for API communication

### 🔹 Model (`model/`)
- Internal representation of data
- Used within service and processor layers

### 🔹 Main Class
`FixforgeApplication.java`
- Entry point of Spring Boot application
- Bootstraps the app

### 🔹 Configuration
`application.properties`
- Defines runtime configuration like:
```
server.port=8080
spring.application.name=fixforge-engine
```

### 🔹 Build File
`build.gradle`
- Manages dependencies
- Defines build lifecycle
- Configures plugins (Spring Boot, Java)

---

## ⚙️ Prerequisites

- Java 17+
- Gradle (or use wrapper)

---

## 🔧 Setup & Installation

```bash
git clone https://github.com/srinayak666/fixforge-engine.git
cd fixforge-engine
```

---

## ▶️ Running the Application

### Using Gradle Wrapper (Recommended)

```bash
./gradlew bootRun
```

### Build and run JAR

```bash
./gradlew build
java -jar build/libs/fixforge-engine.jar
```

App runs at:

```
http://localhost:8080
```

---

## 🔗 API Endpoints (Generic)

| Method | Endpoint     | Description                |
|--------|-------------|----------------------------|
| GET    | /health     | Health check               |
| POST   | /analyze    | Analyze input data         |
| GET    | /results    | Fetch results              |

---

## 🧠 Request Flow

1. Client sends request to API
2. Controller receives request
3. Service processes logic
4. Processor analyzes input
5. Result returned to client

---

## 🧠 Key Features

- ✅ Stateless backend
- ✅ No database dependency
- ✅ Clean layered architecture
- ✅ Modular and scalable design
- ✅ Easy UI integration

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
Change port in `application.properties`

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
