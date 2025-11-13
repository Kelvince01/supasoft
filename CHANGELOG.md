# Changelog

All notable changes to the Supasoft Supermarket Management System will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Planned Features
- Mobile app for stock taking and mobile POS
- Customer loyalty program enhancements
- AI-powered demand forecasting
- Advanced analytics dashboard
- WhatsApp Business integration for notifications
- Automated reorder suggestions based on sales patterns
- Multi-currency support
- Supplier portal

---

## [1.0.0] - 2025-01-15

### Added - Initial Release

#### Core Features
- **Authentication & Authorization**
  - JWT-based authentication system
  - Role-based access control (RBAC)
  - User management with multiple roles (Admin, Manager, Cashier, Stock Clerk, Accountant)
  - Password encryption with BCrypt
  - Token refresh mechanism
  - Session management

- **Item Management**
  - Complete CRUD operations for items
  - Automatic barcode generation (EAN-13, Code-128)
  - Category and sub-category hierarchy
  - Brand management
  - Multiple unit of measure (UOM) support
  - UOM conversion tracking (e.g., 1 carton = 24 pieces)
  - Barcode scanning support
  - Item search and filtering
  - Supplier-item mapping
  - Shelf life and expiry tracking

- **Pricing Management**
  - Multi-tier pricing matrix (Retail, Wholesale, Bulk)
  - Price history tracking
  - Customer-specific pricing
  - Promotional pricing with date ranges
  - Discount management (line-item, invoice-level, promotional)
  - Profit margin calculations
  - VAT handling (16% Kenya standard rate)
  - Price type configuration

- **Stock Management**
  - Real-time stock balance tracking
  - Stock movement recording (purchases, sales, adjustments, transfers)
  - Weighted average cost calculation
  - FIFO for expiry management
  - Batch and expiry date tracking
  - Stock location management
  - Repackaging/bulk breakdown functionality
  - Stock adjustment with approval workflow
  - Stock taking module with variance reports
  - Photo evidence attachment for adjustments
  - Reorder level alerts
  - Stock valuation reports

- **Sales & POS**
  - Point of Sale (POS) operations
  - Sales invoice generation
  - Multiple payment methods (Cash, M-Pesa, Card, Credit)
  - Barcode scanning for quick checkout
  - Discount application
  - Sales returns and refunds
  - Credit note generation
  - Cashier shift management
  - Receipt printing
  - Daily sales reports
  - Customer management

- **Purchase Management**
  - Purchase requisition creation
  - Purchase order (PO) management
  - PO approval workflow
  - Goods Received Note (GRN) from PO
  - Batch and expiry tracking on receipt
  - Quality check workflow
  - Variance reports (PO vs GRN)
  - Supplier management
  - Supplier invoice tracking
  - Approval history

- **Inter-Branch Operations**
  - Stock transfer requests
  - Transfer approval workflow
  - In-transit tracking
  - Receiving at destination branch
  - Transfer variance handling
  - Branch management
  - Consolidated stock view across branches

- **Reporting & Analytics**
  - Daily sales summary reports
  - Item-wise sales analysis
  - Category performance reports
  - Stock movement reports
  - Fast/slow moving items analysis
  - Stock valuation reports
  - Expiry tracking reports
  - Branch performance comparison
  - Profit & loss by item/category
  - Supplier performance reports
  - Customer sales analysis
  - Excel and PDF export functionality

- **Notifications**
  - SMS notifications via Africa's Talking
  - Email notifications
  - Stock alert notifications
  - Low stock alerts
  - Expiry alerts
  - Transfer notifications

- **Integrations**
  - **KRA eTIMS Integration** (Kenya Revenue Authority)
    - Invoice submission to TIMS
    - QR code generation for receipts
    - Compliance reporting
  - **M-Pesa Integration** (Safaricom)
    - STK Push for payments
    - Payment callback handling
    - Transaction reconciliation
    - B2C payments support

#### Technical Features
- **Architecture**
  - Microservices architecture using Spring Boot
  - Service discovery with Netflix Eureka
  - API Gateway with Spring Cloud Gateway
  - Centralized configuration
  - Event-driven communication with RabbitMQ

- **Data Management**
  - MySQL 8.0 database
  - Flyway database migrations
  - Redis caching layer
  - Database replication support
  - Automated backup scripts

- **Security**
  - JWT token authentication
  - Role-based authorization
  - API rate limiting
  - CORS configuration
  - SQL injection prevention
  - XSS protection

- **Monitoring & Operations**
  - Actuator health checks
  - Prometheus metrics export
  - Grafana dashboards
  - Centralized logging
  - Audit trail for critical operations

- **DevOps**
  - Docker containerization
  - Docker Compose for local development
  - GitLab CI/CD pipeline
  - Jenkins pipeline support
  - Automated testing in CI/CD
  - Blue-green deployment support

- **Testing**
  - Unit tests with JUnit 5
  - Integration tests with TestContainers
  - 80%+ code coverage
  - Automated security scanning (OWASP)

- **Documentation**
  - OpenAPI/Swagger documentation
  - Comprehensive user manuals
  - Architecture documentation
  - API contracts documentation
  - Deployment guides
  - Local development setup guides

#### Kenya-Specific Features
- East Africa Time (EAT) timezone support
- Kenya Shillings (KES) currency
- 16% VAT configuration
- KRA eTIMS compliance
- M-Pesa payment integration
- Africa's Talking SMS integration
- Support for returnable containers (empties)
- Multi-location management for Kenyan retail chains

### Technical Stack
- Java 21 (LTS)
- Spring Boot 3.2.x
- Spring Cloud 2023.x
- MySQL 8.0
- Redis 7.x
- RabbitMQ 3.x
- Docker & Docker Compose
- Maven 3.9.x
- Flyway for database migrations
- MapStruct for DTO mapping
- Lombok for boilerplate reduction
- JUnit 5 & Mockito for testing
- Prometheus & Grafana for monitoring

### Deployment
- Docker containers for all services
- Production-ready Docker Compose configuration
- Support for Kubernetes deployment
- Nginx load balancer configuration
- SSL/TLS support
- Database replication setup
- Automated backup procedures

---

## [0.9.0-beta] - 2024-12-20

### Added
- Beta release for user acceptance testing (UAT)
- Core functionality for items, pricing, stock, and sales
- Basic reporting capabilities
- Initial KRA eTIMS integration

### Fixed
- Stock calculation issues with multiple UOMs
- Pricing discrepancies in promotional items
- Session timeout handling

### Known Issues
- Limited reporting options
- M-Pesa integration pending production credentials
- Performance optimization needed for large datasets

---

## [0.8.0-alpha] - 2024-11-30

### Added
- Alpha release for internal testing
- Basic microservices setup
- Item and stock management
- Simple POS functionality

### Technical
- Initial microservices architecture
- Service discovery setup
- API Gateway configuration
- Database schema design

---

## Version History Summary

- **1.0.0** (2025-01-15) - Initial production release
- **0.9.0-beta** (2024-12-20) - Beta release for UAT
- **0.8.0-alpha** (2024-11-30) - Alpha release for internal testing

---

## Upgrade Guide

### From 0.9.0 to 1.0.0

#### Database Changes
```sql
-- Run database migrations
mvn flyway:migrate
```

#### Configuration Updates
- Update `application.yml` with new KRA eTIMS endpoints
- Configure M-Pesa production credentials
- Update Redis connection pool settings

#### Breaking Changes
- API endpoint `/api/items` changed to `/api/v1/items`
- Authentication header format changed to `Bearer <token>`
- Stock movement API response structure updated

### Migration Steps

1. **Backup Database**
   ```bash
   ./scripts/backup-database.sh
   ```

2. **Stop Services**
   ```bash
   docker-compose down
   ```

3. **Update Configuration**
   - Review and update environment variables
   - Update SSL certificates if needed

4. **Run Migrations**
   ```bash
   mvn flyway:migrate
   ```

5. **Deploy New Version**
   ```bash
   docker-compose -f docker-compose-prod.yml up -d
   ```

6. **Verify Deployment**
   - Check health endpoints
   - Verify service registration
   - Run smoke tests

---

## Support

For questions or issues:
- **Documentation**: `/docs` folder
- **Issues**: GitLab/GitHub Issues
- **Email**: support@supasoft.com
- **Website**: https://supasoft.com

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

**Note**: This changelog is maintained by the Supasoft development team. All dates are in ISO 8601 format (YYYY-MM-DD).

