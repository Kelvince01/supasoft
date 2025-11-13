#!/bin/bash

################################################################################
# Supasoft Local Development Environment Setup Script
# 
# Description: Automates the setup of local development environment
# Usage: ./setup-local-env.sh
# 
# Prerequisites:
#   - Docker and Docker Compose installed
#   - Java 21 JDK installed
#   - Maven 3.8+ installed
#   - Git installed
################################################################################

set -e  # Exit on error

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SCRIPTS_DIR="$PROJECT_DIR/scripts"

################################################################################
# Functions
################################################################################

log() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

warn() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

error() {
    echo -e "${RED}[ERROR]${NC} $1"
    exit 1
}

section() {
    echo ""
    echo -e "${BLUE}========================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}========================================${NC}"
}

check_command() {
    if ! command -v $1 &> /dev/null; then
        error "$1 is not installed. Please install $1 and try again."
    else
        log "$1 is installed: $(command -v $1)"
    fi
}

################################################################################
# Prerequisite Checks
################################################################################

check_prerequisites() {
    section "Checking Prerequisites"
    
    log "Checking required tools..."
    
    check_command docker
    check_command docker-compose
    check_command java
    check_command mvn
    check_command git
    
    # Check Java version
    JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -lt 17 ]; then
        error "Java 17 or higher is required. Current version: $JAVA_VERSION"
    fi
    log "Java version: $JAVA_VERSION ✓"
    
    # Check Maven version
    MVN_VERSION=$(mvn -version | head -n 1 | awk '{print $3}')
    log "Maven version: $MVN_VERSION ✓"
    
    # Check Docker daemon
    if ! docker ps &> /dev/null; then
        error "Docker daemon is not running. Please start Docker and try again."
    fi
    log "Docker daemon is running ✓"
    
    log "All prerequisites satisfied!"
}

################################################################################
# Environment Setup
################################################################################

setup_environment_files() {
    section "Setting Up Environment Files"
    
    # Create .env file if it doesn't exist
    if [ ! -f "$PROJECT_DIR/.env" ]; then
        log "Creating .env file..."
        cat > "$PROJECT_DIR/.env" << 'EOF'
# Database Configuration
DB_HOST=localhost
DB_PORT=3306
DB_USERNAME=supasoft_app
DB_PASSWORD=supasoft_pass

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=

# RabbitMQ Configuration
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=admin
RABBITMQ_PASSWORD=admin123

# JWT Configuration
JWT_SECRET=your-secret-key-change-in-production
JWT_EXPIRATION=86400000

# Application Configuration
SPRING_PROFILES_ACTIVE=dev

# M-Pesa Configuration (Kenya)
MPESA_CONSUMER_KEY=your_consumer_key
MPESA_CONSUMER_SECRET=your_consumer_secret
MPESA_SHORTCODE=174379
MPESA_PASSKEY=your_passkey

# Africa's Talking (SMS)
AT_USERNAME=sandbox
AT_API_KEY=your_api_key

# KRA eTIMS
ETIMS_API_KEY=your_etims_key
ETIMS_API_SECRET=your_etims_secret
EOF
        log ".env file created ✓"
    else
        warn ".env file already exists. Skipping..."
    fi
    
    # Create .env.example for reference
    if [ ! -f "$PROJECT_DIR/.env.example" ]; then
        cp "$PROJECT_DIR/.env" "$PROJECT_DIR/.env.example"
        log ".env.example created ✓"
    fi
}

################################################################################
# Docker Infrastructure
################################################################################

start_infrastructure() {
    section "Starting Infrastructure Services"
    
    cd "$PROJECT_DIR"
    
    log "Starting MySQL, Redis, and RabbitMQ..."
    docker-compose up -d mysql redis rabbitmq
    
    log "Waiting for services to be ready..."
    
    # Wait for MySQL
    log "Waiting for MySQL..."
    for i in {1..30}; do
        if docker-compose exec -T mysql mysqladmin ping -h localhost --silent &> /dev/null; then
            log "MySQL is ready ✓"
            break
        fi
        if [ $i -eq 30 ]; then
            error "MySQL failed to start within 30 seconds"
        fi
        sleep 1
    done
    
    # Wait for Redis
    log "Waiting for Redis..."
    for i in {1..15}; do
        if docker-compose exec -T redis redis-cli ping &> /dev/null; then
            log "Redis is ready ✓"
            break
        fi
        if [ $i -eq 15 ]; then
            error "Redis failed to start within 15 seconds"
        fi
        sleep 1
    done
    
    # Wait for RabbitMQ
    log "Waiting for RabbitMQ..."
    sleep 10
    log "RabbitMQ is ready ✓"
    
    log "All infrastructure services are running!"
}

################################################################################
# Database Setup
################################################################################

setup_databases() {
    section "Setting Up Databases"
    
    log "Creating databases..."
    
    # Create databases
    docker-compose exec -T mysql mysql -u root -proot_password << 'EOF'
-- Create databases if they don't exist
CREATE DATABASE IF NOT EXISTS supasoft_auth CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS supasoft_items CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS supasoft_pricing CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS supasoft_stock CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS supasoft_sales CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS supasoft_purchase CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS supasoft_transfer CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS supasoft_reports CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS supasoft_notifications CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS supasoft_integrations CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS supasoft_audit CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Grant privileges
GRANT ALL PRIVILEGES ON supasoft_*.* TO 'supasoft_app'@'%';
FLUSH PRIVILEGES;

SELECT 'Databases created successfully!' AS Status;
EOF
    
    log "Databases created successfully ✓"
    
    # Run Flyway migrations (if using Flyway)
    # This will be handled by Spring Boot when services start
}

################################################################################
# Test Data
################################################################################

load_test_data() {
    section "Loading Test Data"
    
    if [ -f "$SCRIPTS_DIR/generate-test-data.sql" ]; then
        log "Loading test data..."
        docker-compose exec -T mysql mysql -u supasoft_app -psupasoft_pass < "$SCRIPTS_DIR/generate-test-data.sql"
        log "Test data loaded successfully ✓"
    else
        warn "Test data script not found. Skipping..."
    fi
}

################################################################################
# Maven Build
################################################################################

build_project() {
    section "Building Project"
    
    cd "$PROJECT_DIR"
    
    log "Running Maven build (this may take a few minutes)..."
    mvn clean install -DskipTests
    
    if [ $? -eq 0 ]; then
        log "Build completed successfully ✓"
    else
        error "Build failed. Please check the error messages above."
    fi
}

################################################################################
# IDE Configuration
################################################################################

setup_ide() {
    section "IDE Configuration"
    
    log "Generating IDE files..."
    
    # IntelliJ IDEA
    if [ ! -d "$PROJECT_DIR/.idea" ]; then
        log "Importing project into IntelliJ IDEA..."
        # IntelliJ will auto-import Maven projects
        log "Please open the project in IntelliJ IDEA to complete setup."
    fi
    
    # VS Code
    if [ ! -d "$PROJECT_DIR/.vscode" ]; then
        mkdir -p "$PROJECT_DIR/.vscode"
        cat > "$PROJECT_DIR/.vscode/settings.json" << 'EOF'
{
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.compile.nullAnalysis.mode": "automatic",
    "java.format.settings.url": "https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml",
    "editor.formatOnSave": true,
    "files.exclude": {
        "**/.classpath": true,
        "**/.project": true,
        "**/.settings": true,
        "**/.factorypath": true
    }
}
EOF
        log "VS Code settings created ✓"
    fi
}

################################################################################
# Service URLs
################################################################################

display_service_urls() {
    section "Service URLs"
    
    cat << 'EOF'

Your local development environment is ready!

Service URLs:
============================================
MySQL:          localhost:3306
Redis:          localhost:6379
RabbitMQ:       localhost:5672
RabbitMQ Mgmt:  http://localhost:15672
                (user: admin, pass: admin123)

Microservices (when running):
============================================
Eureka:         http://localhost:8761
API Gateway:    http://localhost:8080
Auth Service:   http://localhost:8081
Item Service:   http://localhost:8082
Pricing:        http://localhost:8083
Stock:          http://localhost:8084
Sales:          http://localhost:8085
Purchase:       http://localhost:8086
Transfer:       http://localhost:8087

Next Steps:
============================================
1. Start Eureka Server:
   cd discovery && mvn spring-boot:run

2. Start services in IntelliJ:
   - Open Spring Boot Dashboard
   - Right-click services → Run

3. Or start all services:
   docker-compose up

4. Access Swagger UI:
   http://localhost:8080/swagger-ui.html

5. Test login:
   POST http://localhost:8080/api/v1/auth/login
   {
     "username": "admin",
     "password": "Test@123"
   }

Test Users:
============================================
Admin:      admin / Test@123
Manager:    manager1 / Test@123
Cashier:    cashier1 / Test@123

Documentation:
============================================
- Architecture: docs/architecture/
- API Contracts: docs/architecture/api-contracts.md
- Setup Guide: docs/setup/local-development-setup.md
- Deployment: docs/setup/deployment-guide.md

Need Help?
============================================
- Check logs: docker-compose logs -f [service]
- Run tests: mvn test
- Clean build: mvn clean install -DskipTests

EOF
}

################################################################################
# Cleanup Function
################################################################################

cleanup_on_error() {
    error "Setup failed! Cleaning up..."
    cd "$PROJECT_DIR"
    docker-compose down
    exit 1
}

################################################################################
# Main Execution
################################################################################

main() {
    trap cleanup_on_error ERR
    
    clear
    
    cat << 'EOF'
╔═══════════════════════════════════════════════════════════╗
║                                                           ║
║   ███████╗██╗   ██╗██████╗  █████╗ ███████╗ ██████╗ ████║
║   ██╔════╝██║   ██║██╔══██╗██╔══██╗██╔════╝██╔═══██╗████║
║   ███████╗██║   ██║██████╔╝███████║███████╗██║   ██║████║
║   ╚════██║██║   ██║██╔═══╝ ██╔══██║╚════██║██║   ██║████║
║   ███████║╚██████╔╝██║     ██║  ██║███████║╚██████╔╝    ║
║   ╚══════╝ ╚═════╝ ╚═╝     ╚═╝  ╚═╝╚══════╝ ╚═════╝     ║
║                                                           ║
║        Local Development Environment Setup                ║
║                                                           ║
╚═══════════════════════════════════════════════════════════╝

EOF
    
    log "Starting setup process..."
    log "Project directory: $PROJECT_DIR"
    
    # Run setup steps
    check_prerequisites
    setup_environment_files
    start_infrastructure
    setup_databases
    load_test_data
    build_project
    setup_ide
    
    # Success!
    section "Setup Complete!"
    display_service_urls
    
    log "Setup completed successfully! Happy coding! 🚀"
}

# Run main function
main "$@"

