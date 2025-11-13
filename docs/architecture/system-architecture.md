# Supasoft - System Architecture Documentation

## 1. OVERVIEW

Supasoft is a comprehensive supermarket management system built using **Java Spring Boot microservices architecture**. The system is designed to handle multi-branch retail operations with features including inventory management, point-of-sale (POS), procurement, inter-branch transfers, and regulatory compliance (KRA eTIMS).

### 1.1 Architecture Pattern
- **Pattern**: Modular Monolith transitioning to Microservices
- **Backend**: Spring Boot 3.5.x with Java 21 (LTS)
- **Database**: MySQL 8.0+ (One database per service)
- **API Gateway**: Spring Cloud Gateway
- **Service Discovery**: Netflix Eureka
- **Configuration**: Spring Cloud Config Server
- **Caching**: Redis
- **Message Queue**: RabbitMQ
- **Security**: JWT-based authentication with Spring Security

---

## 2. SYSTEM COMPONENTS

### 2.1 Infrastructure Services

#### Config Server
- **Port**: 8888
- **Purpose**: Centralized configuration management
- **Technology**: Spring Cloud Config
- **Repository**: Git-based configuration store
- **Features**:
  - Environment-specific configurations (dev, prod)
  - Dynamic configuration refresh
  - Encrypted sensitive properties

#### Service Discovery (Eureka)
- **Port**: 8761
- **Purpose**: Service registration and discovery
- **Technology**: Netflix Eureka
- **Features**:
  - Automatic service registration
  - Health monitoring
  - Load balancing support
  - Service failover

#### API Gateway
- **Port**: 8080
- **Purpose**: Single entry point for all client requests
- **Technology**: Spring Cloud Gateway
- **Features**:
  - Request routing
  - Load balancing
  - Rate limiting
  - CORS configuration
  - JWT token validation
  - Request/response logging
  - Circuit breaker integration

### 2.2 Core Business Services

#### Item Service
- **Port**: 8081
- **Database**: `supasoft_items`
- **Responsibilities**:
  - Item master data management
  - Category and brand management
  - Barcode generation (EAN-13, Code-128)
  - Unit of Measure (UOM) management
  - Item search and filtering
  - Multi-attribute item management

#### Pricing Service
- **Port**: 8082
- **Database**: `supasoft_pricing`
- **Responsibilities**:
  - Multi-tier pricing (Retail, Wholesale, Special)
  - Branch-specific pricing
  - UOM-based pricing
  - Promotional pricing
  - Profit margin calculations
  - Price history tracking
  - Dynamic price adjustments

#### Stock Service
- **Port**: 8083
- **Database**: `supasoft_stock`
- **Responsibilities**:
  - Real-time stock balance tracking
  - Stock movements (FIFO/Weighted Average)
  - Batch and expiry management
  - Stock location management
  - Stock adjustments and approvals
  - Repackaging operations
  - Stock valuation (WAC method)
  - Low stock alerts

#### Sales Service (POS)
- **Port**: 8084
- **Database**: `supasoft_sales`
- **Responsibilities**:
  - Point of Sale transactions
  - Barcode scanning
  - Multi-payment processing (Cash, M-Pesa, Card)
  - Discount application
  - Sales returns and refunds
  - Credit note generation
  - Cashier shift management
  - Receipt printing
  - Daily sales reconciliation

#### Purchase Service
- **Port**: 8085
- **Database**: `supasoft_purchase`
- **Responsibilities**:
  - Purchase Order (PO) management
  - PO approval workflow
  - Goods Received Note (GRN)
  - Supplier management
  - Purchase variance tracking
  - Purchase history
  - Supplier performance analytics

#### Transfer Service
- **Port**: 8086
- **Database**: `supasoft_transfer`
- **Responsibilities**:
  - Inter-branch stock transfers
  - Transfer request and approval
  - In-transit tracking
  - Transfer receiving and GRN
  - Variance handling
  - Transfer cost tracking

#### Reports Service
- **Port**: 8087
- **Database**: `supasoft_reports` (Read replicas)
- **Responsibilities**:
  - Daily sales reports
  - Stock movement reports
  - Profit & Loss reports
  - Fast/slow moving items
  - Expiry tracking reports
  - Branch performance analytics
  - Custom report generation
  - PDF/Excel export

### 2.3 Support Services

#### Auth Service
- **Port**: 8090
- **Database**: `supasoft_auth`
- **Responsibilities**:
  - User authentication (JWT)
  - User management
  - Role-based access control (RBAC)
  - Permission management
  - Token generation and validation
  - Password management
  - Session management
  - Multi-tenant support (Branch-level)

#### Notification Service
- **Port**: 8091
- **Database**: `supasoft_notifications`
- **Responsibilities**:
  - Email notifications (SMTP)
  - SMS notifications (Africa's Talking)
  - WhatsApp Business integration
  - Push notifications
  - Notification templates
  - Delivery status tracking
  - Notification scheduling

#### Integration Service
- **Port**: 8092
- **Database**: `supasoft_integrations`
- **Responsibilities**:
  - KRA eTIMS integration
  - M-Pesa STK Push
  - M-Pesa payment callbacks
  - Third-party API integrations
  - Webhook management
  - API rate limiting
  - Integration logs

#### Audit Service
- **Port**: 8093
- **Database**: `supasoft_audit`
- **Responsibilities**:
  - Audit trail logging
  - User activity tracking
  - Data change history
  - Compliance reporting
  - Security event logging
  - Audit log retention

---

## 3. ARCHITECTURAL PATTERNS

### 3.1 Layered Architecture (Per Service)

```
┌─────────────────────────────────────┐
│      Controller Layer               │
│  (REST APIs, Request Validation)    │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Service Layer                  │
│  (Business Logic, Transactions)     │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Repository Layer               │
│  (Data Access, JPA)                 │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Database Layer                 │
│  (MySQL, Redis Cache)               │
└─────────────────────────────────────┘
```

### 3.2 Communication Patterns

#### Synchronous Communication (REST/HTTP)
- API Gateway ↔ Microservices
- Inter-service communication (using RestTemplate/WebClient)
- Client applications ↔ API Gateway

#### Asynchronous Communication (RabbitMQ)
- Event-driven updates (e.g., StockUpdated, SaleCompleted)
- Notification dispatch
- Report generation
- Audit logging

#### Caching Strategy (Redis)
- Item master data (1 hour TTL)
- Pricing data (30 minutes TTL)
- User session data
- API response caching
- Distributed caching across services

### 3.3 Data Management

#### Database Per Service Pattern
Each microservice has its own dedicated MySQL database:
- **Pros**: Service independence, technology flexibility, easier scaling
- **Cons**: Distributed transactions, data consistency challenges

#### Data Consistency Strategies
1. **Saga Pattern**: For distributed transactions (e.g., sales transaction)
2. **Event Sourcing**: For audit trails and history
3. **CQRS**: Read replicas for reporting service
4. **Eventual Consistency**: Accepted for non-critical operations

---

## 4. SECURITY ARCHITECTURE

### 4.1 Authentication Flow

```
1. User Login Request → Auth Service
2. Validate Credentials → Database
3. Generate JWT Token → Return to Client
4. Client Stores Token → Local Storage/Session
5. Subsequent Requests → Include JWT in Header
6. API Gateway → Validates Token
7. Forward Request → Target Microservice
```

### 4.2 Authorization

#### Role-Based Access Control (RBAC)
- **Roles**: SUPER_ADMIN, ADMIN, MANAGER, CASHIER, STOCK_KEEPER
- **Permissions**: Fine-grained (e.g., ITEM_CREATE, SALE_VIEW, REPORT_GENERATE)
- **Branch-Level**: Users assigned to specific branches
- **Multi-Tenant**: Data isolation by branch

#### Security Features
- JWT token expiration (30 minutes access, 7 days refresh)
- Password hashing (BCrypt)
- API rate limiting (100 requests/minute per user)
- CORS configuration
- HTTPS enforcement
- SQL injection prevention (JPA/Hibernate)
- XSS protection
- CSRF protection

---

## 5. SCALABILITY & PERFORMANCE

### 5.1 Horizontal Scaling
- Stateless services (JWT, no server-side sessions)
- Load balancer (Nginx/AWS ALB)
- Multiple instances per service
- Auto-scaling based on CPU/memory

### 5.2 Caching Strategy
```
L1 Cache: Service-level (Caffeine - in-memory)
L2 Cache: Distributed (Redis)
L3 Cache: CDN (for static assets)
```

### 5.3 Database Optimization
- Connection pooling (HikariCP)
- Read replicas for reports
- Indexing strategy
- Query optimization
- Database partitioning (by date for transactions)

### 5.4 Performance Targets
- API response time: < 200ms (p95)
- POS transaction: < 2 seconds end-to-end
- Report generation: < 5 seconds (simple reports)
- System uptime: 99.9%

---

## 6. MONITORING & OBSERVABILITY

### 6.1 Application Monitoring
- **Spring Boot Admin**: Service health, metrics
- **Prometheus**: Metrics collection
- **Grafana**: Dashboards and visualization
- **ELK Stack**: Centralized logging (Elasticsearch, Logstash, Kibana)

### 6.2 Metrics Tracked
- Request throughput (requests/second)
- Response times (p50, p95, p99)
- Error rates
- Database connection pool usage
- Cache hit/miss ratios
- JVM metrics (heap, GC)
- Business metrics (sales/hour, stock levels)

### 6.3 Health Checks
- Liveness probe: `/actuator/health/liveness`
- Readiness probe: `/actuator/health/readiness`
- Custom health indicators for database, Redis, RabbitMQ

---

## 7. DISASTER RECOVERY

### 7.1 Backup Strategy
- **Database**: Automated daily backups (3:00 AM EAT)
- **Retention**: 30 days rolling
- **Location**: Off-site backup storage
- **Testing**: Monthly restore tests

### 7.2 High Availability
- Database replication (Master-Slave)
- Service redundancy (min 2 instances per service)
- Load balancer failover
- Circuit breaker pattern (Resilience4j)

### 7.3 Recovery Time Objectives (RTO)
- **Critical Services** (POS, Auth): < 5 minutes
- **Core Services** (Item, Stock): < 15 minutes
- **Support Services**: < 30 minutes

---

## 8. DEPLOYMENT ARCHITECTURE

### 8.1 Development Environment
```
Docker Compose:
- All services in containers
- Shared MySQL container
- Redis container
- RabbitMQ container
- Local development only
```

### 8.2 Production Environment
```
Cloud Deployment (AWS/Azure):
- EC2/VMs for services
- RDS for MySQL
- ElastiCache for Redis
- Amazon MQ for RabbitMQ
- S3 for file storage
- CloudFront CDN
- Route 53 for DNS
- Application Load Balancer
```

### 8.3 CI/CD Pipeline
```
Git Push → Jenkins/GitLab CI → 
Maven Build → Unit Tests → 
Docker Build → Push to Registry → 
Deploy to Environment → 
Integration Tests → Smoke Tests
```

---

## 9. TECHNOLOGY STACK SUMMARY

| Layer | Technology |
|-------|-----------|
| **Backend** | Spring Boot 3.2.x, Java 21 |
| **Database** | MySQL 8.0+ |
| **ORM** | Hibernate 6.x, Spring Data JPA |
| **Caching** | Redis 7.x |
| **Message Queue** | RabbitMQ 3.x |
| **API Gateway** | Spring Cloud Gateway |
| **Service Discovery** | Netflix Eureka |
| **Security** | Spring Security, JWT (jjwt) |
| **Documentation** | SpringDoc OpenAPI 3 |
| **Logging** | SLF4J, Logback |
| **Monitoring** | Prometheus, Grafana, Spring Boot Admin |
| **Testing** | JUnit 5, Mockito, TestContainers |
| **Build** | Maven 3.9+ |
| **Containerization** | Docker, Docker Compose |
| **Version Control** | Git, GitHub/GitLab |

---

## 10. INTEGRATION POINTS

### 10.1 External Integrations

#### KRA eTIMS (Kenya Revenue Authority)
- **Purpose**: Tax compliance, invoice submission
- **Protocol**: REST API
- **Authentication**: API Key + PIN
- **Features**: Invoice submission, QR code generation, status queries

#### M-Pesa (Safaricom)
- **Purpose**: Mobile payments
- **Protocol**: REST API (STK Push)
- **Authentication**: OAuth 2.0
- **Features**: Payment initiation, callback handling, reconciliation

#### Africa's Talking
- **Purpose**: SMS notifications
- **Protocol**: REST API
- **Features**: Bulk SMS, delivery reports

#### WhatsApp Business API
- **Purpose**: Customer notifications
- **Protocol**: REST API
- **Features**: Template messages, delivery status

---

## 11. SYSTEM CONSTRAINTS & ASSUMPTIONS

### 11.1 Constraints
- Must comply with KRA eTIMS regulations
- Must support offline mode for POS (future enhancement)
- Must handle concurrent transactions (100+ per minute)
- Must support multi-currency (future enhancement)

### 11.2 Assumptions
- Reliable internet connectivity for POS terminals
- MySQL sufficient for current scale (< 10 million transactions/year)
- Single country operation (Kenya)
- EAT timezone (Africa/Nairobi)

---

## 12. FUTURE ENHANCEMENTS

### 12.1 Planned Features
- Mobile app for managers (React Native)
- Offline POS mode with sync
- AI-powered demand forecasting
- Customer loyalty mobile app
- E-commerce integration
- Multi-currency support
- Biometric authentication
- Voice-activated POS

### 12.2 Scalability Roadmap
- Migration to Kubernetes
- Event sourcing for all transactions
- GraphQL API layer
- Microservices split (smaller services)
- Multi-region deployment
- Real-time analytics (Apache Kafka + Flink)

---

**Document Version**: 1.0  
**Last Updated**: November 2025  
**Author**: Supasoft Development Team  
**Review Cycle**: Quarterly

