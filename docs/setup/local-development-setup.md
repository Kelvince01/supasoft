# Supasoft - Local Development Setup Guide

## 1. PREREQUISITES

### 1.1 Required Software

| Software | Version | Download Link |
|----------|---------|---------------|
| **Java JDK** | 21 (LTS) | https://adoptium.net/ |
| **Maven** | 3.9+ | https://maven.apache.org/download.cgi |
| **MySQL** | 8.0+ | https://dev.mysql.com/downloads/mysql/ |
| **Redis** | 7.x | https://redis.io/download |
| **RabbitMQ** | 3.x | https://www.rabbitmq.com/download.html |
| **Git** | Latest | https://git-scm.com/downloads |
| **Docker** (Optional) | Latest | https://www.docker.com/products/docker-desktop |
| **IDE** | IntelliJ IDEA / Eclipse / VS Code | https://www.jetbrains.com/idea/ |

### 1.2 Verify Installations

```bash
# Verify Java
java -version
# Expected: openjdk version "21.0.x"

# Verify Maven
mvn -version
# Expected: Apache Maven 3.9.x

# Verify MySQL
mysql --version
# Expected: mysql Ver 8.0.x

# Verify Redis
redis-cli --version
# Expected: redis-cli 7.x.x

# Verify RabbitMQ
rabbitmqctl version
# Expected: 3.x.x

# Verify Git
git --version
# Expected: git version 2.x.x

# Verify Docker (if using Docker)
docker --version
# Expected: Docker version 24.x.x
```

---

## 2. PROJECT SETUP

### 2.1 Clone Repository

```bash
# Clone the repository
git clone https://github.com/your-org/supasoft-api.git

# Navigate to project directory
cd supasoft-api

# Checkout development branch
git checkout develop
```

### 2.2 Project Structure Verification

```bash
# Verify project structure
ls -la

# Expected output:
# api-gateway/
# auth-service/
# item-service/
# pricing-service/
# stock-service/
# sales-service/
# purchase-service/
# transfer-service/
# reports-service/
# notification-service/
# integration-service/
# audit-service/
# common/
# config-server/
# discovery/
# docker-compose.yml
# pom.xml
# README.md
```

---

## 3. DATABASE SETUP

### 3.1 Option A: Manual MySQL Setup

#### Step 1: Start MySQL Server
```bash
# Start MySQL service
# On Ubuntu/Debian
sudo systemctl start mysql

# On macOS (via Homebrew)
brew services start mysql

# On Windows
# Start MySQL from Services or MySQL Workbench
```

#### Step 2: Create Databases
```bash
# Login to MySQL
mysql -u root -p

# Execute the following SQL commands
```

```sql
-- Create databases for all services
CREATE DATABASE supasoft_auth CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_items CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_pricing CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_stock CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_sales CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_purchase CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_transfer CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_reports CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_notifications CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_integrations CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_audit CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create development user (recommended)
CREATE USER 'supasoft_dev'@'localhost' IDENTIFIED BY 'dev_password_123';

-- Grant permissions
GRANT ALL PRIVILEGES ON supasoft_*.* TO 'supasoft_dev'@'localhost';
FLUSH PRIVILEGES;

-- Verify databases
SHOW DATABASES;

-- Exit
EXIT;
```

#### Step 3: Verify Connection
```bash
# Test connection with new user
mysql -u supasoft_dev -p -e "SHOW DATABASES;"
```

### 3.2 Option B: Docker MySQL Setup

```bash
# Run MySQL in Docker
docker run --name supasoft-mysql \
  -e MYSQL_ROOT_PASSWORD=root_password \
  -e MYSQL_USER=supasoft_dev \
  -e MYSQL_PASSWORD=dev_password_123 \
  -p 3306:3306 \
  -d mysql:8.0

# Wait for MySQL to initialize (30 seconds)
sleep 30

# Create databases using script
docker exec -i supasoft-mysql mysql -u root -proot_password < scripts/create-databases.sql
```

---

## 4. REDIS SETUP

### 4.1 Start Redis Server

```bash
# Option A: Native installation
# On Ubuntu/Debian
sudo systemctl start redis-server

# On macOS (via Homebrew)
brew services start redis

# Option B: Docker
docker run --name supasoft-redis -p 6379:6379 -d redis:7-alpine

# Verify Redis is running
redis-cli ping
# Expected: PONG
```

### 4.2 Configure Redis (Optional)

```bash
# Edit Redis configuration (if needed)
sudo nano /etc/redis/redis.conf

# Key settings for development:
# bind 127.0.0.1
# port 6379
# maxmemory 256mb
# maxmemory-policy allkeys-lru

# Restart Redis after changes
sudo systemctl restart redis-server
```

---

## 5. RABBITMQ SETUP

### 5.1 Start RabbitMQ Server

```bash
# Option A: Native installation
# On Ubuntu/Debian
sudo systemctl start rabbitmq-server

# Enable management plugin
sudo rabbitmq-plugins enable rabbitmq_management

# On macOS (via Homebrew)
brew services start rabbitmq

# Option B: Docker
docker run --name supasoft-rabbitmq \
  -p 5672:5672 \
  -p 15672:15672 \
  -e RABBITMQ_DEFAULT_USER=admin \
  -e RABBITMQ_DEFAULT_PASS=admin123 \
  -d rabbitmq:3-management-alpine

# Verify RabbitMQ is running
rabbitmqctl status
```

### 5.2 Access RabbitMQ Management UI

- **URL**: http://localhost:15672
- **Username**: admin (or guest for default)
- **Password**: admin123 (or guest for default)

---

## 6. BUILD PROJECT

### 6.1 Build Parent Project

```bash
# Navigate to root directory
cd supasoft-api

# Clean and install dependencies
mvn clean install

# Expected output:
# [INFO] BUILD SUCCESS
# [INFO] Total time: XX.XXX s
```

### 6.2 Build Individual Services (Optional)

```bash
# Build specific service
cd item-service
mvn clean package

# Skip tests for faster build
mvn clean package -DskipTests
```

---

## 7. CONFIGURATION

### 7.1 Environment Variables

Create a `.env` file in the root directory:

```bash
# Create .env file
cat > .env << 'EOF'
# Database Configuration
DB_HOST=localhost
DB_PORT=3306
DB_USERNAME=supasoft_dev
DB_PASSWORD=dev_password_123

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379

# RabbitMQ Configuration
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=admin
RABBITMQ_PASSWORD=admin123

# JWT Configuration
JWT_SECRET=your-secret-key-change-this-in-production-min-256-bits
JWT_EXPIRATION=1800000

# KRA eTIMS Configuration (Optional)
ETIMS_API_URL=https://etims-api-sbx.kra.go.ke
ETIMS_API_KEY=your-test-api-key
ETIMS_PIN=your-test-pin

# M-Pesa Configuration (Optional)
MPESA_CONSUMER_KEY=your-consumer-key
MPESA_CONSUMER_SECRET=your-consumer-secret
MPESA_SHORTCODE=174379
MPESA_PASSKEY=your-passkey
MPESA_CALLBACK_URL=http://localhost:8080/api/v1/integrations/mpesa/callback

# Africa's Talking Configuration (Optional)
AT_API_KEY=your-api-key
AT_USERNAME=your-username

# Server Configuration
SERVER_PORT=8080
EUREKA_SERVER_URL=http://localhost:8761/eureka
CONFIG_SERVER_URL=http://localhost:8888
EOF
```

### 7.2 Application Properties

Each service has its own `application-dev.properties` file. Example for item-service:

```bash
# Edit item-service/src/main/resources/application-dev.properties
cat > item-service/src/main/resources/application-dev.properties << 'EOF'
# Server Configuration
server.port=8081

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/supasoft_items?useSSL=false&serverTimezone=Africa/Nairobi
spring.datasource.username=${DB_USERNAME:supasoft_dev}
spring.datasource.password=${DB_PASSWORD:dev_password_123}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# HikariCP Configuration
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Flyway Configuration
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration

# Redis Configuration
spring.cache.type=redis
spring.data.redis.host=${REDIS_HOST:localhost}
spring.data.redis.port=${REDIS_PORT:6379}
spring.cache.redis.time-to-live=3600000

# Eureka Configuration
eureka.client.service-url.defaultZone=${EUREKA_SERVER_URL:http://localhost:8761/eureka}
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.instance.prefer-ip-address=true

# Logging Configuration
logging.level.root=INFO
logging.level.com.supasoft=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n

# Actuator Configuration
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.endpoint.health.show-details=always
EOF
```

---

## 8. RUN SERVICES

### 8.1 Start Services in Order

#### Step 1: Start Config Server (Optional)
```bash
cd config-server
mvn spring-boot:run

# Wait until you see:
# Started ConfigServerApplication in X.XXX seconds
```

#### Step 2: Start Service Discovery (Eureka)
```bash
# Open new terminal
cd discovery
mvn spring-boot:run

# Wait until you see:
# Started DiscoveryApplication in X.XXX seconds

# Access Eureka Dashboard: http://localhost:8761
```

#### Step 3: Start API Gateway
```bash
# Open new terminal
cd api-gateway
mvn spring-boot:run

# Wait until you see:
# Started ApiGatewayApplication in X.XXX seconds
```

#### Step 4: Start Core Services

```bash
# Terminal 4: Auth Service
cd auth-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Terminal 5: Item Service
cd item-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Terminal 6: Pricing Service
cd pricing-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Terminal 7: Stock Service
cd stock-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Terminal 8: Sales Service
cd sales-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Terminal 9: Purchase Service
cd purchase-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Terminal 10: Transfer Service
cd transfer-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Terminal 11: Reports Service
cd reports-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Step 5: Start Support Services (Optional)

```bash
# Terminal 12: Notification Service
cd notification-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Terminal 13: Integration Service
cd integration-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Terminal 14: Audit Service
cd audit-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 8.2 Option B: Docker Compose (All Services)

```bash
# Start all services with Docker Compose
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

### 8.3 Verify Services

```bash
# Check Eureka Dashboard
# Open browser: http://localhost:8761

# Expected registered services:
# - API-GATEWAY
# - AUTH-SERVICE
# - ITEM-SERVICE
# - PRICING-SERVICE
# - STOCK-SERVICE
# - SALES-SERVICE
# - PURCHASE-SERVICE
# - TRANSFER-SERVICE
# - REPORTS-SERVICE
```

---

## 9. DATABASE MIGRATION

### 9.1 Flyway Migrations

Migrations run automatically on service startup. To manually run migrations:

```bash
# Navigate to service directory
cd item-service

# Run Flyway migration
mvn flyway:migrate

# Check migration status
mvn flyway:info

# Clean database (WARNING: Deletes all data)
mvn flyway:clean
```

### 9.2 Create New Migration

```bash
# Create new migration file
# Format: V{version}__{description}.sql
# Example: V3__add_supplier_column_to_items.sql

cd item-service/src/main/resources/db/migration
touch V3__add_supplier_column_to_items.sql

# Edit the file
cat > V3__add_supplier_column_to_items.sql << 'EOF'
ALTER TABLE items ADD COLUMN supplier_id BIGINT NULL;
ALTER TABLE items ADD INDEX idx_supplier_id (supplier_id);
EOF

# Restart service to apply migration
```

---

## 10. SEED DATA

### 10.1 Run Seed Data Script

```bash
# Navigate to scripts directory
cd scripts

# Run generate test data script
mysql -u supasoft_dev -p < generate-test-data.sql

# Or use Docker
docker exec -i supasoft-mysql mysql -u supasoft_dev -p < generate-test-data.sql
```

### 10.2 Default Users

After seeding, the following users are available:

| Username | Password | Role | Description |
|----------|----------|------|-------------|
| `admin` | `Admin@123` | SUPER_ADMIN | System administrator |
| `manager1` | `Manager@123` | MANAGER | Branch manager |
| `cashier1` | `Cashier@123` | CASHIER | POS cashier |
| `stockkeeper1` | `Stock@123` | STOCK_KEEPER | Stock management |

---

## 11. API TESTING

### 11.1 Using Swagger UI

Each service has its own Swagger UI:

- **API Gateway**: http://localhost:8080/swagger-ui.html
- **Item Service**: http://localhost:8081/swagger-ui.html
- **Auth Service**: http://localhost:8090/swagger-ui.html

### 11.2 Using Postman

```bash
# Import Postman collection
# File location: postman/API.postman_collection.json

# Steps:
1. Open Postman
2. Click Import
3. Select File
4. Choose: postman/API.postman_collection.json
5. Collection imported successfully
```

### 11.3 Test Authentication

```bash
# Using curl
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "Admin@123"
  }'

# Expected response:
# {
#   "success": true,
#   "data": {
#     "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
#     "tokenType": "Bearer",
#     "expiresIn": 1800
#   }
# }
```

### 11.4 Test Item Service

```bash
# Get all items (requires authentication)
curl -X GET http://localhost:8080/api/v1/items \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"

# Expected response: Paginated list of items
```

---

## 12. IDE SETUP

### 12.1 IntelliJ IDEA

#### Import Project
```
1. File > Open
2. Select supasoft-api folder
3. Import as Maven project
4. Wait for dependencies to download
```

#### Configure Run Configurations

**For each service:**
```
1. Run > Edit Configurations
2. Click + > Spring Boot
3. Name: Item Service
4. Main class: com.supasoft.item.ItemServiceApplication
5. Working directory: $MODULE_WORKING_DIR$
6. VM options: -Dspring.profiles.active=dev
7. Environment variables: Load from .env file
8. Click OK
```

#### Install Recommended Plugins
- Lombok
- MapStruct Support
- Docker
- Database Tools and SQL
- Spring Boot
- Maven Helper

### 12.2 Visual Studio Code

#### Install Extensions
```bash
# Java Extension Pack
code --install-extension vscjava.vscode-java-pack

# Spring Boot Extension Pack
code --install-extension vmware.vscode-spring-boot

# Lombok
code --install-extension gabrielbb.vscode-lombok

# Docker
code --install-extension ms-azuretools.vscode-docker
```

#### Configure settings.json
```json
{
  "java.configuration.updateBuildConfiguration": "automatic",
  "java.compile.nullAnalysis.mode": "automatic",
  "spring-boot.ls.java.home": "/path/to/java-21",
  "java.home": "/path/to/java-21"
}
```

---

## 13. TROUBLESHOOTING

### 13.1 Port Already in Use

```bash
# Find process using port
# On Linux/macOS
lsof -i :8080

# On Windows
netstat -ano | findstr :8080

# Kill process
# On Linux/macOS
kill -9 <PID>

# On Windows
taskkill /PID <PID> /F
```

### 13.2 Database Connection Issues

```bash
# Verify MySQL is running
sudo systemctl status mysql

# Test connection
mysql -u supasoft_dev -p -h localhost -P 3306

# Check firewall
sudo ufw status

# Reset MySQL password (if forgotten)
sudo mysql -u root
ALTER USER 'supasoft_dev'@'localhost' IDENTIFIED BY 'new_password';
FLUSH PRIVILEGES;
```

### 13.3 Redis Connection Issues

```bash
# Check Redis is running
redis-cli ping

# Check Redis logs
tail -f /var/log/redis/redis-server.log

# Restart Redis
sudo systemctl restart redis-server
```

### 13.4 Service Not Registering with Eureka

```bash
# Check Eureka is running
curl http://localhost:8761/eureka/apps

# Verify application.properties
# Ensure: eureka.client.register-with-eureka=true

# Check network
ping localhost

# Restart service
```

### 13.5 Flyway Migration Errors

```bash
# Check migration status
mvn flyway:info

# Repair migration checksums
mvn flyway:repair

# Clean and re-migrate (WARNING: Deletes all data)
mvn flyway:clean
mvn flyway:migrate

# Manually fix migration
mysql -u supasoft_dev -p
USE supasoft_items;
SELECT * FROM flyway_schema_history;
DELETE FROM flyway_schema_history WHERE version = 'X';
```

### 13.6 Build Failures

```bash
# Clean Maven cache
mvn clean

# Force update dependencies
mvn clean install -U

# Skip tests
mvn clean install -DskipTests

# Delete .m2 cache (last resort)
rm -rf ~/.m2/repository
mvn clean install
```

---

## 14. DEVELOPMENT WORKFLOW

### 14.1 Git Workflow

```bash
# Create feature branch
git checkout -b feature/add-item-images

# Make changes
# ... code changes ...

# Stage changes
git add .

# Commit with meaningful message
git commit -m "feat: Add image upload to item service"

# Push to remote
git push origin feature/add-item-images

# Create Pull Request on GitHub/GitLab
```

### 14.2 Code Style

```bash
# Format code (IntelliJ)
Ctrl + Alt + L (Windows/Linux)
Cmd + Option + L (macOS)

# Organize imports
Ctrl + Alt + O (Windows/Linux)
Cmd + Option + O (macOS)
```

### 14.3 Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ItemServiceTest

# Run specific test method
mvn test -Dtest=ItemServiceTest#testCreateItem

# Generate coverage report
mvn clean test jacoco:report
# Report location: target/site/jacoco/index.html
```

---

## 15. MONITORING & DEBUGGING

### 15.1 Application Logs

```bash
# View logs in real-time
tail -f logs/item-service.log

# Search logs
grep "ERROR" logs/item-service.log

# View last 100 lines
tail -n 100 logs/item-service.log
```

### 15.2 Actuator Endpoints

- **Health**: http://localhost:8081/actuator/health
- **Info**: http://localhost:8081/actuator/info
- **Metrics**: http://localhost:8081/actuator/metrics
- **Environment**: http://localhost:8081/actuator/env

### 15.3 Database Monitoring

```bash
# MySQL slow query log
mysql -u root -p
SET GLOBAL slow_query_log = 'ON';
SET GLOBAL long_query_time = 1;

# Show running queries
SHOW FULL PROCESSLIST;

# Show table status
SHOW TABLE STATUS FROM supasoft_items;
```

### 15.4 Redis Monitoring

```bash
# Monitor Redis commands
redis-cli MONITOR

# Get Redis info
redis-cli INFO

# Check memory usage
redis-cli INFO memory
```

---

## 16. HELPFUL COMMANDS

### 16.1 Maven Commands

```bash
# Build without tests
mvn clean package -DskipTests

# Run specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Generate sources
mvn generate-sources

# Dependency tree
mvn dependency:tree

# Update dependencies
mvn versions:display-dependency-updates
```

### 16.2 Docker Commands

```bash
# Build service image
docker build -t supasoft/item-service:latest ./item-service

# Run service container
docker run -p 8081:8081 supasoft/item-service:latest

# View running containers
docker ps

# View all containers
docker ps -a

# Stop container
docker stop <container_id>

# Remove container
docker rm <container_id>

# View logs
docker logs -f <container_id>

# Execute command in container
docker exec -it <container_id> bash
```

### 16.3 Database Commands

```bash
# Backup database
mysqldump -u supasoft_dev -p supasoft_items > backup.sql

# Restore database
mysql -u supasoft_dev -p supasoft_items < backup.sql

# Export schema only
mysqldump -u supasoft_dev -p --no-data supasoft_items > schema.sql

# Show table sizes
SELECT 
    table_name, 
    ROUND(((data_length + index_length) / 1024 / 1024), 2) AS "Size (MB)"
FROM information_schema.TABLES
WHERE table_schema = "supasoft_items"
ORDER BY (data_length + index_length) DESC;
```

---

## 17. QUICK REFERENCE

### 17.1 Service Ports

| Service | Port | URL |
|---------|------|-----|
| Config Server | 8888 | http://localhost:8888 |
| Eureka Server | 8761 | http://localhost:8761 |
| API Gateway | 8080 | http://localhost:8080 |
| Item Service | 8081 | http://localhost:8081 |
| Pricing Service | 8082 | http://localhost:8082 |
| Stock Service | 8083 | http://localhost:8083 |
| Sales Service | 8084 | http://localhost:8084 |
| Purchase Service | 8085 | http://localhost:8085 |
| Transfer Service | 8086 | http://localhost:8086 |
| Reports Service | 8087 | http://localhost:8087 |
| Auth Service | 8090 | http://localhost:8090 |
| Notification Service | 8091 | http://localhost:8091 |
| Integration Service | 8092 | http://localhost:8092 |
| Audit Service | 8093 | http://localhost:8093 |

### 17.2 Database Ports

| Service | Port |
|---------|------|
| MySQL | 3306 |
| Redis | 6379 |
| RabbitMQ | 5672 |
| RabbitMQ Management | 15672 |

### 17.3 Important URLs

| Resource | URL |
|----------|-----|
| API Gateway | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| Eureka Dashboard | http://localhost:8761 |
| RabbitMQ Management | http://localhost:15672 |

---

## 18. NEXT STEPS

After successful setup:

1. ✅ Verify all services are running
2. ✅ Test authentication endpoint
3. ✅ Create your first item via API
4. ✅ Process a test sale
5. ✅ Generate a sample report
6. ✅ Review API documentation
7. ✅ Start developing features

---

## 19. GETTING HELP

### 19.1 Documentation
- Architecture: `docs/architecture/system-architecture.md`
- API Contracts: `docs/architecture/api-contracts.md`
- Database Schema: `docs/architecture/database-schema.md`

### 19.2 Support Channels
- **Email**: dev-support@supasoft.com
- **Slack**: #supasoft-dev
- **Issue Tracker**: https://github.com/your-org/supasoft-api/issues

### 19.3 Resources
- Spring Boot Docs: https://spring.io/projects/spring-boot
- Spring Cloud Docs: https://spring.io/projects/spring-cloud
- MySQL Docs: https://dev.mysql.com/doc/

---

**Document Version**: 1.0  
**Last Updated**: November 2025  
**Tested On**: Ubuntu 22.04, macOS 14.x, Windows 11  
**Author**: Supasoft Development Team

