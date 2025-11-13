# IntelliJ Spring Initializr Dependencies Guide
## Supermarket Management System

---

## 🏗️ PARENT PROJECT (api)

### Project Settings
```
Name: api
Type: Maven
Packaging: pom
Java: 21
Spring Boot: 3.2.0
```

### Dependencies: NONE
(Parent POM only manages dependencies for child modules)

---

## 🔐 AUTH SERVICE (auth-service)

### Spring Initializr Selection:

#### Developer Tools
- [x] Spring Boot DevTools
- [x] Lombok
- [x] Spring Configuration Processor

#### Web
- [x] Spring Web
- [x] Validation

#### Security
- [x] Spring Security

#### SQL
- [x] Spring Data JPA
- [x] MySQL Driver
- [x] Flyway Migration

#### NoSQL
- [x] Spring Data Redis (Access+Driver)

#### I/O
- [x] Spring Cache Abstraction

#### Ops
- [x] Spring Boot Actuator

---

## 📦 ITEM SERVICE (item-service)

### Spring Initializr Selection:

#### Developer Tools
- [x] Spring Boot DevTools
- [x] Lombok
- [x] Spring Configuration Processor

#### Web
- [x] Spring Web
- [x] Validation

#### Security
- [x] Spring Security

#### SQL
- [x] Spring Data JPA
- [x] MySQL Driver
- [x] Flyway Migration

#### NoSQL
- [x] Spring Data Redis (Access+Driver)

#### I/O
- [x] Spring Cache Abstraction

#### Messaging
- [x] Spring for RabbitMQ (or Apache Kafka)

#### Ops
- [x] Spring Boot Actuator

#### Spring Cloud Discovery
- [x] Eureka Discovery Client

---

## 💰 PRICING SERVICE (pricing-service)

### Spring Initializr Selection:

#### Developer Tools
- [x] Spring Boot DevTools
- [x] Lombok
- [x] Spring Configuration Processor

#### Web
- [x] Spring Web
- [x] Validation

#### Security
- [x] Spring Security

#### SQL
- [x] Spring Data JPA
- [x] MySQL Driver
- [x] Flyway Migration

#### NoSQL
- [x] Spring Data Redis (Access+Driver)

#### I/O
- [x] Spring Cache Abstraction

#### Messaging
- [x] Spring for RabbitMQ

#### Ops
- [x] Spring Boot Actuator

#### Spring Cloud Discovery
- [x] Eureka Discovery Client

---

## 📊 STOCK SERVICE (stock-service)

### Spring Initializr Selection:

#### Developer Tools
- [x] Spring Boot DevTools
- [x] Lombok
- [x] Spring Configuration Processor

#### Web
- [x] Spring Web
- [x] Validation
- [x] Rest Repositories (for HATEOAS if needed)

#### Security
- [x] Spring Security

#### SQL
- [x] Spring Data JPA
- [x] MySQL Driver
- [x] Flyway Migration

#### NoSQL
- [x] Spring Data Redis (Access+Driver)

#### I/O
- [x] Spring Cache Abstraction

#### Messaging
- [x] Spring for RabbitMQ

#### Ops
- [x] Spring Boot Actuator

#### Spring Cloud Discovery
- [x] Eureka Discovery Client

---

## 🛒 SALES/POS SERVICE (sales-service)

### Spring Initializr Selection:

#### Developer Tools
- [x] Spring Boot DevTools
- [x] Lombok
- [x] Spring Configuration Processor

#### Web
- [x] Spring Web
- [x] Validation
- [x] Spring HATEOAS (for REST links)

#### Security
- [x] Spring Security

#### SQL
- [x] Spring Data JPA
- [x] MySQL Driver
- [x] Flyway Migration

#### NoSQL
- [x] Spring Data Redis (Access+Driver)

#### I/O
- [x] Spring Cache Abstraction

#### Messaging
- [x] Spring for RabbitMQ

#### Ops
- [x] Spring Boot Actuator

#### Spring Cloud Discovery
- [x] Eureka Discovery Client

---

## 🚚 PURCHASE SERVICE (purchase-service)

### Spring Initializr Selection:

#### Developer Tools
- [x] Spring Boot DevTools
- [x] Lombok
- [x] Spring Configuration Processor

#### Web
- [x] Spring Web
- [x] Validation

#### Security
- [x] Spring Security

#### SQL
- [x] Spring Data JPA
- [x] MySQL Driver
- [x] Flyway Migration

#### Messaging
- [x] Spring for RabbitMQ

#### Ops
- [x] Spring Boot Actuator

#### Spring Cloud Discovery
- [x] Eureka Discovery Client

---

## 🔄 TRANSFER SERVICE (transfer-service)

### Spring Initializr Selection:

#### Developer Tools
- [x] Spring Boot DevTools
- [x] Lombok
- [x] Spring Configuration Processor

#### Web
- [x] Spring Web
- [x] Validation

#### Security
- [x] Spring Security

#### SQL
- [x] Spring Data JPA
- [x] MySQL Driver
- [x] Flyway Migration

#### Messaging
- [x] Spring for RabbitMQ

#### Ops
- [x] Spring Boot Actuator

#### Spring Cloud Discovery
- [x] Eureka Discovery Client

---

## 📈 REPORTS SERVICE (reports-service)

### Spring Initializr Selection:

#### Developer Tools
- [x] Spring Boot DevTools
- [x] Lombok
- [x] Spring Configuration Processor

#### Web
- [x] Spring Web
- [x] Validation

#### Security
- [x] Spring Security

#### SQL
- [x] Spring Data JPA
- [x] MySQL Driver

#### NoSQL
- [x] Spring Data Redis (Access+Driver)

#### I/O
- [x] Spring Cache Abstraction

#### Ops
- [x] Spring Boot Actuator

#### Spring Cloud Discovery
- [x] Eureka Discovery Client

---

## 🌐 API GATEWAY (api-gateway)

### Spring Initializr Selection:

#### Developer Tools
- [x] Spring Boot DevTools
- [x] Lombok

#### Spring Cloud Routing
- [x] Gateway

#### Spring Cloud Discovery
- [x] Eureka Discovery Client

#### Security
- [x] Spring Security
- [x] OAuth2 Resource Server

#### NoSQL
- [x] Spring Data Redis (for rate limiting)

#### Ops
- [x] Spring Boot Actuator

---

## 🔍 SERVICE DISCOVERY (discovery)

### Spring Initializr Selection:

#### Developer Tools
- [x] Spring Boot DevTools

#### Spring Cloud Discovery
- [x] Eureka Server

#### Ops
- [x] Spring Boot Actuator

---

## ⚙️ CONFIG SERVER (config-server)

### Spring Initializr Selection:

#### Developer Tools
- [x] Spring Boot DevTools

#### Spring Cloud Config
- [x] Config Server

#### Ops
- [x] Spring Boot Actuator

---

## 📨 NOTIFICATION SERVICE (notification-service)

### Spring Initializr Selection:

#### Developer Tools
- [x] Spring Boot DevTools
- [x] Lombok

#### Web
- [x] Spring Web

#### SQL
- [x] Spring Data JPA
- [x] MySQL Driver

#### Messaging
- [x] Spring for RabbitMQ

#### I/O
- [x] Java Mail Sender

#### Ops
- [x] Spring Boot Actuator

#### Spring Cloud Discovery
- [x] Eureka Discovery Client

---

## 🔌 INTEGRATION SERVICE (integration-service)

### Spring Initializr Selection:

#### Developer Tools
- [x] Spring Boot DevTools
- [x] Lombok

#### Web
- [x] Spring Web
- [x] Spring Reactive Web (for async calls)

#### SQL
- [x] Spring Data JPA
- [x] MySQL Driver

#### Messaging
- [x] Spring for RabbitMQ

#### Ops
- [x] Spring Boot Actuator

#### Spring Cloud Discovery
- [x] Eureka Discovery Client

---

## 📝 COMMON LIBRARY (common)

### Spring Initializr Selection:

#### Developer Tools
- [x] Lombok
- [x] Spring Configuration Processor

#### Web
- [x] Validation

#### Security
- [x] Spring Security (for shared security configs)

**Note:** This is a library module, so select minimal dependencies.

---

## 🧪 ADDITIONAL DEPENDENCIES (Add Manually in pom.xml)

### For All Services:
```xml
<!-- MapStruct for DTO mapping -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>

<!-- API Documentation -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.2.0</version>
</dependency>
```

### For Reports Service:
```xml
<!-- Excel Generation -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.5</version>
</dependency>

<!-- PDF Generation -->
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.13.3</version>
</dependency>
```

### For Integration Service (Kenya-specific):
```xml
<!-- M-Pesa Integration -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
</dependency>

<!-- Africa's Talking SMS -->
<dependency>
    <groupId>com.africastalking</groupId>
    <artifactId>core</artifactId>
    <version>3.4.10</version>
</dependency>
```

### For Testing (All Services):
```xml
<!-- Testcontainers -->
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>mysql</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>
```

---

## 🎯 PRIORITY ORDER FOR DEVELOPMENT

### Phase 1 (Start Here):
1. **api** (POM setup)
2. **common** (Shared utilities)
3. **discovery** (Service registry)
4. **config-server** (Configuration)
5. **auth-service** (Authentication)

### Phase 2 (Core Services):
6. **item-service**
7. **pricing-service**
8. **stock-service**

### Phase 3 (Business Services):
9. **purchase-service**
10. **sales-service**
11. **transfer-service**

### Phase 4 (Support Services):
12. **reports-service**
13. **notification-service**
14. **integration-service**
15. **api-gateway** (Last)

---

## 💡 INTELLIJ TIPS

### Enable Auto-Import:
1. File → Settings → Editor → General → Auto Import
2. Check "Add unambiguous imports on the fly"
3. Check "Optimize imports on the fly"

### Spring Boot Dashboard:
1. View → Tool Windows → Services
2. All Spring Boot apps will appear here
3. Start/stop services easily

### Live Templates:
1. Settings → Editor → Live Templates
2. Add templates for common patterns:
    - `svc` → Service class template
    - `ctrl` → Controller template
    - `repo` → Repository template

### Lombok Plugin:
1. File → Settings → Plugins
2. Install "Lombok" plugin
3. Enable annotation processing:
   Settings → Build → Compiler → Annotation Processors
   Check "Enable annotation processing"

---

## 📋 CHECKLIST BEFORE STARTING

- [ ] Java 21 JDK installed
- [ ] MySQL 8.0+ installed and running
- [ ] Redis installed (for caching)
- [ ] Maven 3.8+ configured in IntelliJ
- [ ] Lombok plugin installed
- [ ] Git configured
- [ ] Docker installed (for Testcontainers)
- [ ] Postman/Insomnia installed (API testing)

---

## 🚀 QUICK START COMMAND

After creating each service in IntelliJ:

```bash
# From parent directory
mvn clean install

# Run specific service
cd auth-service
mvn spring-boot:run

# Or use IntelliJ's Spring Boot Dashboard
```

---

**Pro Tip:** Start with a single monolithic service first (combine item, pricing, stock) to validate the architecture, then split into microservices once the domain is well understood.
