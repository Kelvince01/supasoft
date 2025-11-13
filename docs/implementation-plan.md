# Supasoft - Supermarket Management System - Implementation Plan
## Java Spring Boot Microservices Architecture

---

## 1. ARCHITECTURE OVERVIEW

### 1.1 Technology Stack
```
Backend Framework: Spring Boot 3.2.x
Java Version: Java 21 (LTS)
Database: MySQL 8.0+
ORM: Hibernate 6.x (via Spring Data JPA)
Build Tool: Maven / Gradle
API Documentation: SpringDoc OpenAPI (Swagger)
Security: Spring Security + JWT
Caching: Redis
Message Queue: RabbitMQ
Logging: SLF4J + Logback
Testing: JUnit 5, Mockito, TestContainers
```

### 1.2 Architecture Pattern
**Modular Monolith** transitioning to **Microservices**

```
api/
│
├── common-lib/                    # Shared utilities
├── config-server/                 # Centralized configuration
├── api-gateway/                   # API Gateway (Spring Cloud Gateway)
├── service-discovery/             # Eureka Server
│
├── core-services/
│   ├── item-service/             # Item & Inventory Management
│   ├── pricing-service/          # Pricing & Promotions
│   ├── stock-service/            # Stock Management
│   ├── sales-service/            # Sales & POS
│   ├── purchase-service/         # Procurement
│   ├── transfer-service/         # Inter-branch Transfers
│   └── reports-service/          # Reporting & Analytics
│
└── support-services/
    ├── auth-service/             # Authentication & Authorization
    ├── notification-service/     # SMS, Email notifications
    ├── integration-service/      # KRA eTIMS, M-Pesa
    └── audit-service/            # Audit trails
```

---

## 2. PROJECT STRUCTURE (Maven Multi-Module)

### 2.1 Root POM Structure
```
api/
│
├── pom.xml (Parent POM - Dependency Management)
│
├── common/
│   ├── models/           # Shared DTOs, Enums
│   ├── utils/            # Utilities, Helpers
│   ├── exceptions/       # Custom Exceptions
│   └── security/         # Security configs
│
├── config-server/
├── api-gateway/
├── discovery/
│
├── item-service/
├── pricing-service/
├── stock-service/
├── sales-service/
├── purchase-service/
├── transfer-service/
├── reports-service/
│
└── auth-service/
```

### 2.2 Individual Service Structure (Layered Architecture)
```
item-service/
│
├── src/main/java/com/supasoft/item/
│   ├── ItemServiceApplication.java
│   │
│   ├── config/                  # Configuration classes
│   │   ├── DatabaseConfig.java
│   │   ├── SecurityConfig.java
│   │   ├── CacheConfig.java
│   │   └── SwaggerConfig.java
│   │
│   ├── controller/              # REST Controllers
│   │   ├── ItemController.java
│   │   ├── CategoryController.java
│   │   └── BarcodeController.java
│   │
│   ├── service/                 # Business Logic
│   │   ├── ItemService.java
│   │   ├── ItemServiceImpl.java
│   │   ├── CategoryService.java
│   │   └── BarcodeService.java
│   │
│   ├── repository/              # Data Access Layer
│   │   ├── ItemRepository.java
│   │   ├── CategoryRepository.java
│   │   └── ItemBarcodeRepository.java
│   │
│   ├── entity/                  # JPA Entities
│   │   ├── Item.java
│   │   ├── Category.java
│   │   ├── Brand.java
│   │   └── ItemBarcode.java
│   │
│   ├── dto/                     # Data Transfer Objects
│   │   ├── request/
│   │   │   ├── CreateItemRequest.java
│   │   │   └── UpdateItemRequest.java
│   │   └── response/
│   │       ├── ItemResponse.java
│   │       └── ItemListResponse.java
│   │
│   ├── mapper/                  # Entity ↔ DTO Mappers
│   │   └── ItemMapper.java      # Using MapStruct
│   │
│   ├── exception/               # Custom Exceptions
│   │   ├── ItemNotFoundException.java
│   │   └── DuplicateBarcodeException.java
│   │
│   ├── validation/              # Custom Validators
│   │   └── BarcodeValidator.java
│   │
│   ├── event/                   # Event Publishers/Listeners
│   │   ├── ItemCreatedEvent.java
│   │   └── ItemEventPublisher.java
│   │
│   └── util/                    # Utility Classes
│       └── BarcodeGenerator.java
│
├── src/main/resources/
│   ├── application.yml
│   ├── application-dev.yml
│   ├── application-prod.yml
│   ├── db/migration/            # Flyway/Liquibase migrations
│   │   ├── V1__create_items_table.sql
│   │   └── V2__create_categories_table.sql
│   └── messages/
│       └── messages.properties
│
└── src/test/java/
    ├── controller/              # Controller tests
    ├── service/                 # Service tests
    └── repository/              # Repository tests
```

---

## 3. IMPLEMENTATION PHASES

### **PHASE 1: Foundation Setup (Week 1-2)**

#### Sprint 1.1: Infrastructure Setup
- [ ] Setup parent POM with dependency management
- [ ] Create common-lib module with shared utilities
- [ ] Setup MySQL database and connection pooling
- [ ] Configure Flyway/Liquibase for database migrations
- [ ] Setup Spring Boot Admin for monitoring
- [ ] Configure logging with SLF4J and Logback
- [ ] Setup CI/CD pipeline (Jenkins/GitLab CI)

#### Sprint 1.2: Security & Authentication
- [ ] Implement auth-service with JWT
- [ ] Create User, Role, Permission entities
- [ ] Implement login/logout endpoints
- [ ] Add password encryption (BCrypt)
- [ ] Implement token refresh mechanism
- [ ] Add API rate limiting
- [ ] Setup CORS configuration

**Deliverables:**
```java
// Example: JWT Token Service
@Service
public class JwtTokenService {
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    
    public String generateToken(Authentication authentication);
    public boolean validateToken(String token);
    public String getUsernameFromToken(String token);
}
```

---

### **PHASE 2: Master Data Management (Week 3-5)**

#### Sprint 2.1: Item Management Service
- [ ] Create Item entities (Item, Category, Brand, UOM)
- [ ] Implement CRUD operations for items
- [ ] Add barcode generation (EAN-13, Code-128)
- [ ] Implement category hierarchy
- [ ] Add item search with filters
- [ ] Implement pagination and sorting
- [ ] Add Redis caching for frequently accessed items

**Key Components:**
```java
// Entity Example
@Entity
@Table(name = "items")
@EntityListeners(AuditingEntityListener.class)
public class Item extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    
    @Column(unique = true, nullable = false)
    private String itemCode;
    
    @Column(unique = true)
    private String barcode;
    
    @Column(nullable = false)
    private String itemName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal vatRate = new BigDecimal("16.00");
    
    private Boolean isActive = true;
    
    // Getters, Setters, equals, hashCode
}

// Repository Example
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, 
                                       JpaSpecificationExecutor<Item> {
    
    Optional<Item> findByItemCode(String itemCode);
    Optional<Item> findByBarcode(String barcode);
    
    @Query("SELECT i FROM Item i WHERE i.category.categoryId = :categoryId " +
           "AND i.isActive = true")
    Page<Item> findActiveItemsByCategory(
        @Param("categoryId") Long categoryId, 
        Pageable pageable
    );
    
    @Query("SELECT i FROM Item i WHERE " +
           "LOWER(i.itemName) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(i.barcode) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Item> searchItems(@Param("search") String search, Pageable pageable);
}

// Service Example
@Service
@Transactional
@Slf4j
public class ItemServiceImpl implements ItemService {
    
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final BarcodeGenerator barcodeGenerator;
    private final ApplicationEventPublisher eventPublisher;
    
    @Cacheable(value = "items", key = "#itemId")
    public ItemResponse getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException(itemId));
        return itemMapper.toResponse(item);
    }
    
    @CacheEvict(value = "items", allEntries = true)
    public ItemResponse createItem(CreateItemRequest request) {
        // Validate barcode uniqueness
        if (request.getBarcode() != null && 
            itemRepository.findByBarcode(request.getBarcode()).isPresent()) {
            throw new DuplicateBarcodeException(request.getBarcode());
        }
        
        Item item = itemMapper.toEntity(request);
        
        // Generate barcode if not provided
        if (item.getBarcode() == null) {
            item.setBarcode(barcodeGenerator.generateEAN13());
        }
        
        Item savedItem = itemRepository.save(item);
        
        // Publish event
        eventPublisher.publishEvent(new ItemCreatedEvent(this, savedItem));
        
        log.info("Created new item: {}", savedItem.getItemCode());
        
        return itemMapper.toResponse(savedItem);
    }
}

// Controller Example
@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    
    private final ItemService itemService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
    public ResponseEntity<PagedResponse<ItemResponse>> getAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId) {
        
        return ResponseEntity.ok(itemService.getAllItems(page, size, search, categoryId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ItemResponse> createItem(
            @Valid @RequestBody CreateItemRequest request) {
        ItemResponse response = itemService.createItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ItemResponse> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody UpdateItemRequest request) {
        return ResponseEntity.ok(itemService.updateItem(id, request));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ItemResponse> getItemByBarcode(@PathVariable String barcode) {
        return ResponseEntity.ok(itemService.getItemByBarcode(barcode));
    }
}
```

#### Sprint 2.2: Pricing Service
- [ ] Implement multi-tier pricing matrix
- [ ] Create price type management
- [ ] Add branch-specific pricing
- [ ] Implement UOM-based pricing
- [ ] Add profit margin calculations
- [ ] Create promotional pricing
- [ ] Add price history tracking

#### Sprint 2.3: Supplier & Customer Management
- [ ] Create Supplier CRUD operations
- [ ] Implement Customer management
- [ ] Add customer categories
- [ ] Implement credit limit tracking
- [ ] Add loyalty points system
- [ ] Create supplier-item mapping

**Deliverables:** Complete Master Data APIs

---

### **PHASE 3: Stock Management (Week 6-8)**

#### Sprint 3.1: Stock Balance & Movements
- [ ] Create stock_balance table and entity
- [ ] Implement stock movement tracking
- [ ] Add weighted average cost calculation
- [ ] Implement batch and expiry tracking
- [ ] Create stock valuation reports
- [ ] Add stock location management

**Key Implementation:**
```java
@Service
@Transactional
public class StockService {
    
    // Weighted Average Cost Calculation
    public void updateStockOnPurchase(GRNItem grnItem) {
        StockBalance stock = getOrCreateStockBalance(
            grnItem.getItem(), 
            grnItem.getBranch(), 
            grnItem.getBatchNumber()
        );
        
        BigDecimal oldValue = stock.getQuantity()
            .multiply(stock.getAverageCost());
        BigDecimal newValue = grnItem.getReceivedQuantity()
            .multiply(grnItem.getUnitCost());
        
        BigDecimal totalQuantity = stock.getQuantity()
            .add(grnItem.getReceivedQuantity());
        BigDecimal totalValue = oldValue.add(newValue);
        
        BigDecimal newAverageCost = totalValue.divide(
            totalQuantity, 
            2, 
            RoundingMode.HALF_UP
        );
        
        stock.setAverageCost(newAverageCost);
        stock.setQuantity(totalQuantity);
        
        // Record movement
        recordStockMovement(
            grnItem.getItem(),
            MovementType.PURCHASE,
            grnItem.getReceivedQuantity(),
            newAverageCost
        );
    }
}
```

#### Sprint 3.2: Stock Adjustments & Taking
- [ ] Implement stock adjustment module
- [ ] Add variance tracking
- [ ] Create approval workflow
- [ ] Implement photo evidence upload (S3/MinIO)
- [ ] Add adjustment reports
- [ ] Create cycle counting schedule

#### Sprint 3.3: Repackaging Module
- [ ] Implement bulk-to-retail conversion
- [ ] Create repackaging records
- [ ] Add source/destination tracking
- [ ] Implement cost allocation
- [ ] Add repackaging reports

**Deliverables:** Stock Management APIs

---

### **PHASE 4: Purchase Management (Week 9-10)**

#### Sprint 4.1: Purchase Orders
- [ ] Create PO module with workflow
- [ ] Implement PO approval chain
- [ ] Add supplier selection
- [ ] Create PO templates
- [ ] Implement PO status tracking
- [ ] Add email notifications

#### Sprint 4.2: Goods Received Notes (GRN)
- [ ] Implement GRN creation from PO
- [ ] Add batch and expiry tracking
- [ ] Implement quality check workflow
- [ ] Create GRN posting to stock
- [ ] Add variance reports (PO vs GRN)

**Deliverables:** Complete Procurement Module

---

### **PHASE 5: Sales & POS (Week 11-13)**

#### Sprint 5.1: Point of Sale
- [ ] Create sales invoice module
- [ ] Implement barcode scanning
- [ ] Add multi-payment methods (Cash, M-Pesa, Card)
- [ ] Implement discount application
- [ ] Add cashier shift management
- [ ] Create receipt printing

**POS Service:**
```java
@Service
@Transactional
public class POSService {
    
    public SalesInvoice processSale(CreateSaleRequest request) {
        // 1. Validate stock availability
        validateStockAvailability(request.getItems());
        
        // 2. Create invoice
        SalesInvoice invoice = createInvoice(request);
        
        // 3. Process each item
        for (SaleItemRequest item : request.getItems()) {
            // Get price
            BigDecimal price = pricingService.getPrice(
                item.getItemId(), 
                item.getUomId(), 
                request.getCustomerId()
            );
            
            // Calculate totals
            SalesInvoiceItem invoiceItem = buildInvoiceItem(item, price);
            invoice.addItem(invoiceItem);
            
            // Deduct stock (FIFO for expiry)
            stockService.deductStock(
                item.getItemId(),
                item.getQuantity(),
                request.getBranchId()
            );
        }
        
        // 4. Apply discounts
        applyDiscounts(invoice, request.getDiscounts());
        
        // 5. Calculate totals
        invoice.calculateTotals();
        
        // 6. Process payment
        processPayment(invoice, request.getPayments());
        
        // 7. Generate KRA eTIMS invoice
        String cuInvoiceNumber = etimsService.submitInvoice(invoice);
        invoice.setCuInvoiceNumber(cuInvoiceNumber);
        
        // 8. Save invoice
        SalesInvoice savedInvoice = salesInvoiceRepository.save(invoice);
        
        // 9. Publish event
        eventPublisher.publishEvent(new SaleCompletedEvent(savedInvoice));
        
        return savedInvoice;
    }
}
```

#### Sprint 5.2: Returns & Refunds
- [ ] Implement sales returns
- [ ] Add credit note generation
- [ ] Create refund processing
- [ ] Implement stock return to inventory

#### Sprint 5.3: KRA eTIMS Integration
- [ ] Integrate with KRA TIMS API
- [ ] Implement invoice submission
- [ ] Add QR code generation
- [ ] Create compliance reports

**Deliverables:** Working POS System

---

### **PHASE 6: Inter-Branch Operations (Week 14-15)**

#### Sprint 6.1: Stock Transfers
- [ ] Create transfer request module
- [ ] Implement approval workflow
- [ ] Add in-transit tracking
- [ ] Create GRN at destination
- [ ] Implement variance handling

**Transfer Service:**
```java
@Service
@Transactional
public class TransferService {
    
    @Async
    public void processTransfer(Long transferId) {
        StockTransfer transfer = transferRepository.findById(transferId)
            .orElseThrow(() -> new TransferNotFoundException(transferId));
        
        // 1. Validate stock at source
        validateSourceStock(transfer);
        
        // 2. Deduct from source branch
        transfer.getItems().forEach(item -> {
            stockService.deductStock(
                item.getItem().getItemId(),
                item.getSentQuantity(),
                transfer.getFromBranch().getBranchId(),
                MovementType.TRANSFER_OUT,
                transfer.getTransferNumber()
            );
        });
        
        transfer.setStatus(TransferStatus.IN_TRANSIT);
        transfer.setSentAt(LocalDateTime.now());
        
        // 3. Send notification to receiving branch
        notificationService.notifyTransferInTransit(transfer);
        
        transferRepository.save(transfer);
    }
    
    public void receiveTransfer(Long transferId, List<ReceiveItemRequest> items) {
        StockTransfer transfer = transferRepository.findById(transferId)
            .orElseThrow();
        
        // 1. Update received quantities
        items.forEach(receivedItem -> {
            StockTransferItem item = transfer.getItems().stream()
                .filter(i -> i.getItem().getItemId().equals(receivedItem.getItemId()))
                .findFirst()
                .orElseThrow();
            
            item.setReceivedQuantity(receivedItem.getQuantity());
            item.setVarianceReason(receivedItem.getVarianceReason());
        });
        
        // 2. Add to destination stock
        transfer.getItems().forEach(item -> {
            if (item.getReceivedQuantity().compareTo(BigDecimal.ZERO) > 0) {
                stockService.addStock(
                    item.getItem().getItemId(),
                    item.getReceivedQuantity(),
                    transfer.getToBranch().getBranchId(),
                    item.getUnitCost(),
                    MovementType.TRANSFER_IN,
                    transfer.getTransferNumber()
                );
            }
        });
        
        transfer.setStatus(TransferStatus.RECEIVED);
        transfer.setReceivedDate(LocalDate.now());
        
        transferRepository.save(transfer);
        
        // 3. Generate variance report if any
        if (hasVariance(transfer)) {
            reportService.generateTransferVarianceReport(transfer);
        }
    }
}
```

**Deliverables:** Transfer Module

---

### **PHASE 7: Special Features (Week 16-17)**

#### Sprint 7.1: Empties Management
- [ ] Create empty container tracking
- [ ] Implement deposit/refund system
- [ ] Add container balance tracking
- [ ] Create reconciliation reports

#### Sprint 7.2: Stock Issues & Promotions
- [ ] Implement stock issue module
- [ ] Add approval workflow
- [ ] Create promotional campaigns
- [ ] Implement "Buy X Get Y" offers
- [ ] Add discount combinations

#### Sprint 7.3: Reorder Management
- [ ] Create reorder point alerts
- [ ] Implement EOQ calculations
- [ ] Add auto-PO generation
- [ ] Create demand forecasting
- [ ] Implement seasonal adjustments

**Deliverables:** Special Features

---

### **PHASE 8: Reporting & Analytics (Week 18-19)**

#### Sprint 8.1: Operational Reports
- [ ] Daily sales summary
- [ ] Stock movement reports
- [ ] Fast/slow moving items
- [ ] Expiry tracking reports
- [ ] Branch performance reports

#### Sprint 8.2: Financial Reports
- [ ] Profit & Loss by item/category
- [ ] Stock valuation reports
- [ ] Purchase analysis
- [ ] Supplier performance
- [ ] Customer analysis

#### Sprint 8.3: Dashboard & Analytics
- [ ] Create management dashboard
- [ ] Add real-time sales charts
- [ ] Implement KPI tracking
- [ ] Create alert system
- [ ] Add predictive analytics

**Reporting Service:**
```java
@Service
public class ReportService {
    
    public SalesSummaryReport generateDailySalesReport(
            Long branchId, 
            LocalDate date) {
        
        // Use native query for performance
        List<Object[]> results = salesInvoiceRepository
            .getDailySalesAnalytics(branchId, date);
        
        return SalesSummaryReport.builder()
            .date(date)
            .totalSales(calculateTotalSales(results))
            .totalProfit(calculateTotalProfit(results))
            .transactionCount(results.size())
            .topSellingItems(getTopSellingItems(results, 10))
            .salesByCategory(groupByCategory(results))
            .hourlyBreakdown(groupByHour(results))
            .build();
    }
    
    @Async
    public void generateAndEmailReport(ReportRequest request) {
        byte[] pdfReport = generatePDF(request);
        emailService.sendReportEmail(request.getRecipient(), pdfReport);
    }
}
```

**Deliverables:** Complete Reporting Module

---

### **PHASE 9: Integration & Additional Features (Week 20-21)**

#### Sprint 9.1: M-Pesa Integration
- [ ] Integrate Safaricom M-Pesa API
- [ ] Implement STK Push
- [ ] Add payment callback handling
- [ ] Create payment reconciliation

#### Sprint 9.2: Notification Service
- [ ] Implement SMS notifications (Africa's Talking)
- [ ] Add email notifications
- [ ] Create WhatsApp Business integration
- [ ] Implement push notifications

#### Sprint 9.3: Mobile API
- [ ] Create mobile-optimized APIs
- [ ] Implement offline sync
- [ ] Add mobile barcode scanning
- [ ] Create mobile stock taking

**Deliverables:** Integration APIs

---

### **PHASE 10: Testing & Deployment (Week 22-24)**

#### Sprint 10.1: Testing
- [ ] Unit tests (80%+ coverage)
- [ ] Integration tests
- [ ] Performance testing (JMeter)
- [ ] Security testing (OWASP)
- [ ] User acceptance testing (UAT)

#### Sprint 10.2: Documentation
- [ ] API documentation (Swagger/OpenAPI)
- [ ] User manuals
- [ ] Deployment guides
- [ ] Database documentation
- [ ] Code documentation (Javadoc)

#### Sprint 10.3: Deployment
- [ ] Setup production environment
- [ ] Configure load balancer
- [ ] Setup database replication
- [ ] Configure backup strategy
- [ ] Implement monitoring (Prometheus + Grafana)
- [ ] Setup alerting
- [ ] Create disaster recovery plan

**Deliverables:** Production-ready System

---

## 4. KEY DEPENDENCIES (pom.xml)

```xml
<properties>
    <java.version>17</java.version>
    <spring-boot.version>3.2.0</spring-boot.version>
    <spring-cloud.version>2023.0.0</spring-cloud.version>
</properties>

<dependencies>
    <!-- Spring Boot Starters -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
    
    <!-- Database -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-core</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-mysql</artifactId>
    </dependency>
    
    <!-- Redis -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    
    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.3</version>
    </dependency>
    
    <!-- MapStruct -->
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>1.5.5.Final</version>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    
    <!-- API Documentation -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.2.0</version>
    </dependency>
    
    <!-- Excel/PDF Generation -->
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.2.3</version>
    </dependency>
    
    <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itextpdf</artifactId>
        <version>5.5.13.3</version>
    </dependency>
    
    <!-- Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>mysql</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 5. CONFIGURATION FILES

### application.yml
```yaml
spring:
  application:
    name: item-service
  
  datasource:
    url: jdbc:mysql://localhost:3306/supasoft_db?useSSL=false&serverTimezone=Africa/Nairobi
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:password}
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQL8Dialect
  
  flyway:
    enabled: true
    baseline-on-migrate: true
    locations: classpath:db/migration
  
  cache:
    type: redis
    redis:
      time-to-live: 3600000 # 1 hour
  
  redis:
    host: localhost
    port: 6379

server:
  port: 8081
