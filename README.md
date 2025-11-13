# SUPASOFT Supermarket Management System

A comprehensive microservices-based supermarket management system built with Spring Boot.

## Features

- 🛒 Point of Sale (POS)
- 📦 Inventory Management
- 💰 Pricing & Promotions
- 🚚 Purchase Orders & GRN
- 🔄 Inter-branch Transfers
- 📊 Reports & Analytics
- 🔐 Role-based Access Control
- 💳 M-Pesa Integration
- 📱 SMS Notifications
- 🧾 KRA eTIMS Compliance

## Technology Stack

- **Backend**: Spring Boot 3.2.0, Java 21
- **Database**: MySQL 8.0
- **Cache**: Redis
- **Message Queue**: RabbitMQ
- **Service Discovery**: Eureka
- **API Gateway**: Spring Cloud Gateway
- **Documentation**: SpringDoc OpenAPI

## Prerequisites

- Java 21 or higher
- Maven 3.8+
- MySQL 8.0+
- Redis 7+
- RabbitMQ 3+
- Docker & Docker Compose (optional)

## Quick Start

### Using Docker Compose (Recommended)

```bash
# Clone repository
git clone https://github.com/Kelvince01/supasoft.git
cd supasoft

# Copy environment file
cp .env.example .env

# Edit .env with your configurations
nano .env

# Start all services
docker-compose up -d

# Check logs
docker-compose logs -f
```

### Manual Setup

```bash
# 1. Start infrastructure
docker-compose up -d mysql redis rabbitmq

# 2. Build all services
mvn clean install

# 3. Start services in order
cd discovery && mvn spring-boot:run &
cd config-server && mvn spring-boot:run &
cd auth-service && mvn spring-boot:run &
cd item-service && mvn spring-boot:run &
# ... start other services
```

## API Documentation

Once services are running, access Swagger UI:

- Item Service: <http://localhost:8082/swagger-ui.html>
- Auth Service: <http://localhost:8081/swagger-ui.html>
- API Gateway: <http://localhost:8080/swagger-ui.html>

## Project Structure

See [ARCHITECTURE.md](docs/architecture/system-architecture.md) for detailed structure.

## Development

```bash
# Run tests
mvn test

# Run specific service
cd item-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md)

## License

MIT License - see [LICENSE](LICENSE)
