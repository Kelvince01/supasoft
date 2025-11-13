```
api/
│
├── .gitignore
├── .editorconfig
├── .env.example
├── README.md
├── LICENSE
├── CHANGELOG.md
├── CONTRIBUTING.md
├── docker-compose.yml                    # Local development infrastructure
├── docker-compose-prod.yml               # Production infrastructure
├── Dockerfile                            # Multi-stage build for services
├── .gitlab-ci.yml                        # CI/CD pipeline (or .github/workflows/)
├── Jenkinsfile                           # Jenkins pipeline
├── pom.xml                               # Parent POM (Dependency Management)
├── mvnw                                  # Maven wrapper
├── mvnw.cmd                              # Maven wrapper (Windows)
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
│
├── docs/                                 # Documentation
│   ├── architecture/
│   │   ├── system-architecture.md
│   │   ├── database-schema.md
│   │   └── api-contracts.md
│   ├── setup/
│   │   ├── local-development-setup.md
│   │   └── deployment-guide.md
│   └── user-manuals/
│       ├── pos-user-guide.md
│       └── admin-guide.md
│
├── scripts/                              # Utility scripts
│   ├── setup-local-env.sh
│   ├── generate-test-data.sql
│   └── backup-database.sh
│
├── postman/                              # API Testing
│   └── API.postman_collection.json
│
│
├── common/                   # Shared Library Module
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/common/
│       │   │   ├── config/               # Shared configurations
│       │   │   │   ├── AuditConfig.java
│       │   │   │   ├── JacksonConfig.java
│       │   │   │   └── WebConfig.java
│       │   │   │
│       │   │   ├── dto/                  # Shared DTOs
│       │   │   │   ├── BaseResponse.java
│       │   │   │   ├── PagedResponse.java
│       │   │   │   ├── ErrorResponse.java
│       │   │   │   └── ApiResponse.java
│       │   │   │
│       │   │   ├── entity/               # Base entities
│       │   │   │   ├── BaseEntity.java
│       │   │   │   └── AuditableEntity.java
│       │   │   │
│       │   │   ├── enums/                # Shared enums
│       │   │   │   ├── Status.java
│       │   │   │   ├── TransactionType.java
│       │   │   │   └── UserRole.java
│       │   │   │
│       │   │   ├── exception/            # Common exceptions
│       │   │   │   ├── GlobalExceptionHandler.java
│       │   │   │   ├── ResourceNotFoundException.java
│       │   │   │   ├── ValidationException.java
│       │   │   │   └── BusinessException.java
│       │   │   │
│       │   │   ├── security/             # Security utilities
│       │   │   │   ├── JwtUtil.java
│       │   │   │   ├── SecurityUtil.java
│       │   │   │   └── JwtAuthenticationFilter.java
│       │   │   │
│       │   │   ├── util/                 # Utility classes
│       │   │   │   ├── DateUtil.java
│       │   │   │   ├── StringUtil.java
│       │   │   │   ├── ValidationUtil.java
│       │   │   │   └── FileUtil.java
│       │   │   │
│       │   │   └── constant/             # Constants
│       │   │       ├── AppConstants.java
│       │   │       └── MessageConstants.java
│       │   │
│       │   └── resources/
│       │       ├── messages.properties
│       │       └── application-common.yml
│       │
│       └── test/
│           └── java/com/supasoft/common/
│               └── util/
│                   └── DateUtilTest.java
│
│
├── discovery/                # Service Discovery (Eureka Server)
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/discovery/
│       │   │   └── DiscoveryServerApplication.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       └── banner.txt
│       │
│       └── test/
│           └── java/com/supasoft/discovery/
│               └── DiscoveryServerApplicationTests.java
│
│
├── config-server/            # Config Server
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/config/
│       │   │   └── ConfigServerApplication.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       └── configs/              # Centralized configs
│       │           ├── item-service.yml
│       │           ├── stock-service.yml
│       │           └── sales-service.yml
│       │
│       └── test/
│           └── java/com/supasoft/config/
│               └── ConfigServerApplicationTests.java
│
│
├── auth-service/             # Authentication Service
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/auth/
│       │   │   ├── AuthServiceApplication.java
│       │   │   │
│       │   │   ├── config/
│       │   │   │   ├── SecurityConfig.java
│       │   │   │   ├── JwtConfig.java
│       │   │   │   └── SwaggerConfig.java
│       │   │   │
│       │   │   ├── controller/
│       │   │   │   ├── AuthController.java
│       │   │   │   ├── UserController.java
│       │   │   │   └── RoleController.java
│       │   │   │
│       │   │   ├── service/
│       │   │   │   ├── AuthService.java
│       │   │   │   ├── AuthServiceImpl.java
│       │   │   │   ├── UserService.java
│       │   │   │   ├── UserServiceImpl.java
│       │   │   │   ├── JwtTokenService.java
│       │   │   │   └── CustomUserDetailsService.java
│       │   │   │
│       │   │   ├── repository/
│       │   │   │   ├── UserRepository.java
│       │   │   │   ├── RoleRepository.java
│       │   │   │   ├── PermissionRepository.java
│       │   │   │   └── RefreshTokenRepository.java
│       │   │   │
│       │   │   ├── entity/
│       │   │   │   ├── User.java
│       │   │   │   ├── Role.java
│       │   │   │   ├── Permission.java
│       │   │   │   └── RefreshToken.java
│       │   │   │
│       │   │   ├── dto/
│       │   │   │   ├── request/
│       │   │   │   │   ├── LoginRequest.java
│       │   │   │   │   ├── RegisterRequest.java
│       │   │   │   │   ├── RefreshTokenRequest.java
│       │   │   │   │   └── ChangePasswordRequest.java
│       │   │   │   └── response/
│       │   │   │       ├── LoginResponse.java
│       │   │   │       ├── UserResponse.java
│       │   │   │       └── TokenResponse.java
│       │   │   │
│       │   │   ├── mapper/
│       │   │   │   └── UserMapper.java
│       │   │   │
│       │   │   ├── exception/
│       │   │   │   ├── InvalidCredentialsException.java
│       │   │   │   ├── UserAlreadyExistsException.java
│       │   │   │   └── TokenExpiredException.java
│       │   │   │
│       │   │   └── security/
│       │   │       ├── JwtAuthenticationEntryPoint.java
│       │   │       └── JwtAuthorizationFilter.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-dev.yml
│       │       ├── application-prod.yml
│       │       └── db/
│       │           └── migration/
│       │               ├── V1__create_users_table.sql
│       │               ├── V2__create_roles_table.sql
│       │               ├── V3__create_permissions_table.sql
│       │               └── V4__insert_default_roles.sql
│       │
│       └── test/
│           └── java/com/supasoft/auth/
│               ├── controller/
│               │   └── AuthControllerTest.java
│               ├── service/
│               │   └── AuthServiceTest.java
│               └── repository/
│                   └── UserRepositoryTest.java
│
│
├── item-service/             # Item Management Service
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/item/
│       │   │   ├── ItemServiceApplication.java
│       │   │   │
│       │   │   ├── config/
│       │   │   │   ├── DatabaseConfig.java
│       │   │   │   ├── SecurityConfig.java
│       │   │   │   ├── CacheConfig.java
│       │   │   │   ├── SwaggerConfig.java
│       │   │   │   └── EventConfig.java
│       │   │   │
│       │   │   ├── controller/
│       │   │   │   ├── ItemController.java
│       │   │   │   ├── CategoryController.java
│       │   │   │   ├── BrandController.java
│       │   │   │   ├── UnitOfMeasureController.java
│       │   │   │   └── BarcodeController.java
│       │   │   │
│       │   │   ├── service/
│       │   │   │   ├── ItemService.java
│       │   │   │   ├── ItemServiceImpl.java
│       │   │   │   ├── CategoryService.java
│       │   │   │   ├── CategoryServiceImpl.java
│       │   │   │   ├── BrandService.java
│       │   │   │   ├── BrandServiceImpl.java
│       │   │   │   ├── BarcodeService.java
│       │   │   │   ├── BarcodeServiceImpl.java
│       │   │   │   └── UomService.java
│       │   │   │
│       │   │   ├── repository/
│       │   │   │   ├── ItemRepository.java
│       │   │   │   ├── CategoryRepository.java
│       │   │   │   ├── BrandRepository.java
│       │   │   │   ├── UnitOfMeasureRepository.java
│       │   │   │   ├── ItemBarcodeRepository.java
│       │   │   │   └── ItemUomConversionRepository.java
│       │   │   │
│       │   │   ├── entity/
│       │   │   │   ├── Item.java
│       │   │   │   ├── Category.java
│       │   │   │   ├── Brand.java
│       │   │   │   ├── UnitOfMeasure.java
│       │   │   │   ├── ItemBarcode.java
│       │   │   │   ├── ItemUomConversion.java
│       │   │   │   └── Supplier.java
│       │   │   │
│       │   │   ├── dto/
│       │   │   │   ├── request/
│       │   │   │   │   ├── CreateItemRequest.java
│       │   │   │   │   ├── UpdateItemRequest.java
│       │   │   │   │   ├── CreateCategoryRequest.java
│       │   │   │   │   ├── CreateBrandRequest.java
│       │   │   │   │   └── UomConversionRequest.java
│       │   │   │   └── response/
│       │   │   │       ├── ItemResponse.java
│       │   │   │       ├── ItemDetailResponse.java
│       │   │   │       ├── CategoryResponse.java
│       │   │   │       ├── BrandResponse.java
│       │   │   │       └── ItemListResponse.java
│       │   │   │
│       │   │   ├── mapper/
│       │   │   │   ├── ItemMapper.java
│       │   │   │   ├── CategoryMapper.java
│       │   │   │   └── BrandMapper.java
│       │   │   │
│       │   │   ├── exception/
│       │   │   │   ├── ItemNotFoundException.java
│       │   │   │   ├── DuplicateBarcodeException.java
│       │   │   │   ├── CategoryNotFoundException.java
│       │   │   │   └── InvalidUomConversionException.java
│       │   │   │
│       │   │   ├── validation/
│       │   │   │   ├── BarcodeValidator.java
│       │   │   │   └── UniqueBarcode.java
│       │   │   │
│       │   │   ├── event/
│       │   │   │   ├── ItemCreatedEvent.java
│       │   │   │   ├── ItemUpdatedEvent.java
│       │   │   │   ├── ItemDeletedEvent.java
│       │   │   │   └── ItemEventPublisher.java
│       │   │   │
│       │   │   ├── specification/
│       │   │   │   └── ItemSpecification.java
│       │   │   │
│       │   │   └── util/
│       │   │       ├── BarcodeGenerator.java
│       │   │       └── ItemCodeGenerator.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-dev.yml
│       │       ├── application-prod.yml
│       │       ├── db/
│       │       │   └── migration/
│       │       │       ├── V1__create_categories_table.sql
│       │       │       ├── V2__create_brands_table.sql
│       │       │       ├── V3__create_uom_table.sql
│       │       │       ├── V4__create_items_table.sql
│       │       │       ├── V5__create_item_barcodes_table.sql
│       │       │       ├── V6__create_item_uom_conversion_table.sql
│       │       │       ├── V7__create_suppliers_table.sql
│       │       │       └── V8__insert_default_data.sql
│       │       │
│       │       └── messages/
│       │           └── validation-messages.properties
│       │
│       └── test/
│           └── java/com/supasoft/item/
│               ├── controller/
│               │   ├── ItemControllerTest.java
│               │   └── CategoryControllerTest.java
│               ├── service/
│               │   ├── ItemServiceTest.java
│               │   └── CategoryServiceTest.java
│               ├── repository/
│               │   ├── ItemRepositoryTest.java
│               │   └── CategoryRepositoryTest.java
│               └── integration/
│                   └── ItemIntegrationTest.java
│
│
├── pricing-service/          # Pricing Management Service
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/pricing/
│       │   │   ├── PricingServiceApplication.java
│       │   │   │
│       │   │   ├── config/
│       │   │   │   ├── DatabaseConfig.java
│       │   │   │   ├── SecurityConfig.java
│       │   │   │   ├── CacheConfig.java
│       │   │   │   └── SwaggerConfig.java
│       │   │   │
│       │   │   ├── controller/
│       │   │   │   ├── PricingController.java
│       │   │   │   ├── DiscountController.java
│       │   │   │   ├── PromotionController.java
│       │   │   │   └── PriceHistoryController.java
│       │   │   │
│       │   │   ├── service/
│       │   │   │   ├── PricingService.java
│       │   │   │   ├── PricingServiceImpl.java
│       │   │   │   ├── DiscountService.java
│       │   │   │   ├── DiscountServiceImpl.java
│       │   │   │   ├── PromotionService.java
│       │   │   │   ├── PromotionServiceImpl.java
│       │   │   │   ├── PriceCalculationService.java
│       │   │   │   └── ProfitMarginService.java
│       │   │   │
│       │   │   ├── repository/
│       │   │   │   ├── ItemPriceRepository.java
│       │   │   │   ├── PriceTypeRepository.java
│       │   │   │   ├── DiscountRepository.java
│       │   │   │   ├── PromotionRepository.java
│       │   │   │   ├── PriceHistoryRepository.java
│       │   │   │   └── CustomerPricingRepository.java
│       │   │   │
│       │   │   ├── entity/
│       │   │   │   ├── ItemPrice.java
│       │   │   │   ├── PriceType.java
│       │   │   │   ├── Discount.java
│       │   │   │   ├── Promotion.java
│       │   │   │   ├── PromotionItem.java
│       │   │   │   ├── PriceHistory.java
│       │   │   │   └── CustomerPricing.java
│       │   │   │
│       │   │   ├── dto/
│       │   │   │   ├── request/
│       │   │   │   │   ├── CreatePriceRequest.java
│       │   │   │   │   ├── UpdatePriceRequest.java
│       │   │   │   │   ├── CreateDiscountRequest.java
│       │   │   │   │   ├── CreatePromotionRequest.java
│       │   │   │   │   └── PriceCalculationRequest.java
│       │   │   │   └── response/
│       │   │   │       ├── PriceResponse.java
│       │   │   │       ├── DiscountResponse.java
│       │   │   │       ├── PromotionResponse.java
│       │   │   │       ├── PriceCalculationResponse.java
│       │   │   │       └── ProfitMarginResponse.java
│       │   │   │
│       │   │   ├── mapper/
│       │   │   │   ├── PricingMapper.java
│       │   │   │   └── PromotionMapper.java
│       │   │   │
│       │   │   ├── exception/
│       │   │   │   ├── PriceNotFoundException.java
│       │   │   │   ├── InvalidDiscountException.java
│       │   │   │   └── PromotionExpiredException.java
│       │   │   │
│       │   │   ├── enums/
│       │   │   │   ├── PriceTypeEnum.java
│       │   │   │   ├── DiscountType.java
│       │   │   │   └── PromotionType.java
│       │   │   │
│       │   │   └── util/
│       │   │       ├── PriceCalculator.java
│       │   │       └── MarginCalculator.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-dev.yml
│       │       └── db/
│       │           └── migration/
│       │               ├── V1__create_price_types_table.sql
│       │               ├── V2__create_item_prices_table.sql
│       │               ├── V3__create_discounts_table.sql
│       │               ├── V4__create_promotions_table.sql
│       │               ├── V5__create_promotion_items_table.sql
│       │               ├── V6__create_price_history_table.sql
│       │               └── V7__create_customer_pricing_table.sql
│       │
│       └── test/
│           └── java/com/supasoft/pricing/
│               ├── service/
│               │   └── PriceCalculationServiceTest.java
│               └── util/
│                   └── PriceCalculatorTest.java
│
│
├── stock-service/            # Stock Management Service
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/stock/
│       │   │   ├── StockServiceApplication.java
│       │   │   │
│       │   │   ├── config/
│       │   │   │   ├── DatabaseConfig.java
│       │   │   │   ├── SecurityConfig.java
│       │   │   │   ├── CacheConfig.java
│       │   │   │   ├── SwaggerConfig.java
│       │   │   │   └── AsyncConfig.java
│       │   │   │
│       │   │   ├── controller/
│       │   │   │   ├── StockBalanceController.java
│       │   │   │   ├── StockMovementController.java
│       │   │   │   ├── StockAdjustmentController.java
│       │   │   │   ├── StockTakingController.java
│       │   │   │   ├── RepackagingController.java
│       │   │   │   └── ReorderController.java
│       │   │   │
│       │   │   ├── service/
│       │   │   │   ├── StockBalanceService.java
│       │   │   │   ├── StockBalanceServiceImpl.java
│       │   │   │   ├── StockMovementService.java
│       │   │   │   ├── StockMovementServiceImpl.java
│       │   │   │   ├── StockAdjustmentService.java
│       │   │   │   ├── StockAdjustmentServiceImpl.java
│       │   │   │   ├── StockTakingService.java
│       │   │   │   ├── StockTakingServiceImpl.java
│       │   │   │   ├── RepackagingService.java
│       │   │   │   ├── RepackagingServiceImpl.java
│       │   │   │   ├── ReorderService.java
│       │   │   │   ├── ReorderServiceImpl.java
│       │   │   │   ├── CostCalculationService.java
│       │   │   │   └── ValuationService.java
│       │   │   │
│       │   │   ├── repository/
│       │   │   │   ├── StockBalanceRepository.java
│       │   │   │   ├── StockMovementRepository.java
│       │   │   │   ├── StockAdjustmentRepository.java
│       │   │   │   ├── StockAdjustmentItemRepository.java
│       │   │   │   ├── StockTakingRepository.java
│       │   │   │   ├── StockTakingItemRepository.java
│       │   │   │   ├── RepackagingRepository.java
│       │   │   │   ├── ReorderLevelRepository.java
│       │   │   │   └── BatchRepository.java
│       │   │   │
│       │   │   ├── entity/
│       │   │   │   ├── StockBalance.java
│       │   │   │   ├── StockMovement.java
│       │   │   │   ├── StockAdjustment.java
│       │   │   │   ├── StockAdjustmentItem.java
│       │   │   │   ├── StockTaking.java
│       │   │   │   ├── StockTakingItem.java
│       │   │   │   ├── Repackaging.java
│       │   │   │   ├── RepackagingItem.java
│       │   │   │   ├── ReorderLevel.java
│       │   │   │   ├── Batch.java
│       │   │   │   └── StockLocation.java
│       │   │   │
│       │   │   ├── dto/
│       │   │   │   ├── request/
│       │   │   │   │   ├── StockAdjustmentRequest.java
│       │   │   │   │   ├── StockTakingRequest.java
│       │   │   │   │   ├── RepackagingRequest.java
│       │   │   │   │   ├── ReorderLevelRequest.java
│       │   │   │   │   └── StockMovementRequest.java
│       │   │   │   └── response/
│       │   │   │       ├── StockBalanceResponse.java
│       │   │   │       ├── StockMovementResponse.java
│       │   │   │       ├── StockValuationResponse.java
│       │   │   │       ├── ReorderAlertResponse.java
│       │   │   │       └── VarianceReportResponse.java
│       │   │   │
│       │   │   ├── mapper/
│       │   │   │   ├── StockMapper.java
│       │   │   │   └── RepackagingMapper.java
│       │   │   │
│       │   │   ├── exception/
│       │   │   │   ├── InsufficientStockException.java
│       │   │   │   ├── BatchExpiredException.java
│       │   │   │   └── InvalidRepackagingException.java
│       │   │   │
│       │   │   ├── enums/
│       │   │   │   ├── MovementType.java
│       │   │   │   ├── AdjustmentReason.java
│       │   │   │   └── StockStatus.java
│       │   │   │
│       │   │   ├── event/
│       │   │   │   ├── StockUpdateEvent.java
│       │   │   │   ├── StockAlertEvent.java
│       │   │   │   └── StockEventPublisher.java
│       │   │   │
│       │   │   └── util/
│       │   │       ├── WeightedAverageCostCalculator.java
│       │   │       └── FifoCalculator.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-dev.yml
│       │       └── db/
│       │           └── migration/
│       │               ├── V1__create_stock_balance_table.sql
│       │               ├── V2__create_stock_movement_table.sql
│       │               ├── V3__create_stock_adjustment_table.sql
│       │               ├── V4__create_stock_taking_table.sql
│       │               ├── V5__create_repackaging_table.sql
│       │               ├── V6__create_reorder_level_table.sql
│       │               ├── V7__create_batch_table.sql
│       │               └── V8__create_stock_location_table.sql
│       │
│       └── test/
│           └── java/com/supasoft/stock/
│               ├── service/
│               │   ├── StockBalanceServiceTest.java
│               │   └── CostCalculationServiceTest.java
│               └── util/
│                   └── WeightedAverageCostCalculatorTest.java
│
│
├── sales-service/            # Sales & POS Service
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/sales/
│       │   │   ├── SalesServiceApplication.java
│       │   │   │
│       │   │   ├── config/
│       │   │   │   ├── DatabaseConfig.java
│       │   │   │   ├── SecurityConfig.java
│       │   │   │   ├── SwaggerConfig.java
│       │   │   │   └── MessagingConfig.java
│       │   │   │
│       │   │   ├── controller/
│       │   │   │   ├── POSController.java
│       │   │   │   ├── SalesInvoiceController.java
│       │   │   │   ├── SalesReturnController.java
│       │   │   │   ├── PaymentController.java
│       │   │   │   ├── ShiftController.java
│       │   │   │   └── CustomerController.java
│       │   │   │
│       │   │   ├── service/
│       │   │   │   ├── POSService.java
│       │   │   │   ├── POSServiceImpl.java
│       │   │   │   ├── SalesInvoiceService.java
│       │   │   │   ├── SalesInvoiceServiceImpl.java
│       │   │   │   ├── SalesReturnService.java
│       │   │   │   ├── SalesReturnServiceImpl.java
│       │   │   │   ├── PaymentService.java
│       │   │   │   ├── PaymentServiceImpl.java
│       │   │   │   ├── ShiftService.java
│       │   │   │   ├── ShiftServiceImpl.java
│       │   │   │   ├── ReceiptService.java
│       │   │   │   ├── ReceiptServiceImpl.java
│       │   │   │   └── CustomerService.java
│       │   │   │
│       │   │   ├── repository/
│       │   │   │   ├── SalesInvoiceRepository.java
│       │   │   │   ├── SalesInvoiceItemRepository.java
│       │   │   │   ├── SalesReturnRepository.java
│       │   │   │   ├── SalesReturnItemRepository.java
│       │   │   │   ├── PaymentRepository.java
│       │   │   │   ├── ShiftRepository.java
│       │   │   │   └── CustomerRepository.java
│       │   │   │
│       │   │   ├── entity/
│       │   │   │   ├── SalesInvoice.java
│       │   │   │   ├── SalesInvoiceItem.java
│       │   │   │   ├── SalesReturn.java
│       │   │   │   ├── SalesReturnItem.java
│       │   │   │   ├── Payment.java
│       │   │   │   ├── Shift.java
│       │   │   │   ├── Customer.java
│       │   │   │   └── LoyaltyPoint.java
│       │   │   │
│       │   │   ├── dto/
│       │   │   │   ├── request/
│       │   │   │   │   ├── CreateSaleRequest.java
│       │   │   │   │   ├── SaleItemRequest.java
│       │   │   │   │   ├── PaymentRequest.java
│       │   │   │   │   ├── CreateReturnRequest.java
│       │   │   │   │   ├── OpenShiftRequest.java
│       │   │   │   │   └── CloseShiftRequest.java
│       │   │   │   └── response/
│       │   │   │       ├── SalesInvoiceResponse.java
│       │   │   │       ├── ReceiptResponse.java
│       │   │   │       ├── ShiftSummaryResponse.java
│       │   │   │       ├── DailySalesResponse.java
│       │   │   │       └── CustomerSalesResponse.java
│       │   │   │
│       │   │   ├── mapper/
│       │   │   │   ├── SalesMapper.java
│       │   │   │   └── PaymentMapper.java
│       │   │   │
│       │   │   ├── exception/
│       │   │   │   ├── InsufficientPaymentException.java
│       │   │   │   ├── InvalidReturnException.java
│       │   │   │   └── ShiftNotOpenException.java
│       │   │   │
│       │   │   ├── enums/
│       │   │   │   ├── PaymentMethod.java
│       │   │   │   ├── InvoiceStatus.java
│       │   │   │   └── ReturnReason.java
│       │   │   │
│       │   │   ├── event/
│       │   │   │   ├── SaleCompletedEvent.java
│       │   │   │   ├── SaleReturnedEvent.java
│       │   │   │   └── SalesEventPublisher.java
│       │   │   │
│       │   │   └── util/
│       │   │       └── ReceiptGenerator.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-dev.yml
│       │       ├── db/
│       │       │   └── migration/
│       │       │       ├── V1__create_customers_table.sql
│       │       │       ├── V2__create_sales_invoice_table.sql
│       │       │       ├── V3__create_sales_invoice_items_table.sql
│       │       │       ├── V4__create_payments_table.sql
│       │       │       ├── V5__create_sales_return_table.sql
│       │       │       ├── V6__create_shifts_table.sql
│       │       │       └── V7__create_loyalty_points_table.sql
│       │       │
│       │       └── templates/
│       │           └── receipt-template.html
│       │
│       └── test/
│           └── java/com/supasoft/sales/
│               ├── controller/
│               │   └── POSControllerTest.java
│               ├── service/
│               │   ├── POSServiceTest.java
│               │   └── ShiftServiceTest.java
│               └── integration/
│                   └── SalesIntegrationTest.java
│
│
├── purchase-service/         # Purchase/Procurement Service
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/purchase/
│       │   │   ├── PurchaseServiceApplication.java
│       │   │   │
│       │   │   ├── config/
│       │   │   │   ├── DatabaseConfig.java
│       │   │   │   ├── SecurityConfig.java
│       │   │   │   ├── SwaggerConfig.java
│       │   │   │   └── WorkflowConfig.java
│       │   │   │
│       │   │   ├── controller/
│       │   │   │   ├── PurchaseOrderController.java
│       │   │   │   ├── PurchaseRequisitionController.java
│       │   │   │   ├── GRNController.java
│       │   │   │   ├── SupplierController.java
│       │   │   │   └── SupplierInvoiceController.java
│       │   │   │
│       │   │   ├── service/
│       │   │   │   ├── PurchaseOrderService.java
│       │   │   │   ├── PurchaseOrderServiceImpl.java
│       │   │   │   ├── PurchaseRequisitionService.java
│       │   │   │   ├── PurchaseRequisitionServiceImpl.java
│       │   │   │   ├── GRNService.java
│       │   │   │   ├── GRNServiceImpl.java
│       │   │   │   ├── SupplierService.java
│       │   │   │   ├── SupplierServiceImpl.java
│       │   │   │   ├── ApprovalService.java
│       │   │   │   └── SupplierInvoiceService.java
│       │   │   │
│       │   │   ├── repository/
│       │   │   │   ├── PurchaseOrderRepository.java
│       │   │   │   ├── PurchaseOrderItemRepository.java
│       │   │   │   ├── PurchaseRequisitionRepository.java
│       │   │   │   ├── GRNRepository.java
│       │   │   │   ├── GRNItemRepository.java
│       │   │   │   ├── SupplierRepository.java
│       │   │   │   └── SupplierInvoiceRepository.java
│       │   │   │
│       │   │   ├── entity/
│       │   │   │   ├── PurchaseOrder.java
│       │   │   │   ├── PurchaseOrderItem.java
│       │   │   │   ├── PurchaseRequisition.java
│       │   │   │   ├── PurchaseRequisitionItem.java
│       │   │   │   ├── GoodsReceivedNote.java
│       │   │   │   ├── GRNItem.java
│       │   │   │   ├── Supplier.java
│       │   │   │   ├── SupplierInvoice.java
│       │   │   │   └── ApprovalHistory.java
│       │   │   │
│       │   │   ├── dto/
│       │   │   │   ├── request/
│       │   │   │   │   ├── CreatePORequest.java
│       │   │   │   │   ├── CreateRequisitionRequest.java
│       │   │   │   │   ├── CreateGRNRequest.java
│       │   │   │   │   ├── ApprovalRequest.java
│       │   │   │   │   └── CreateSupplierRequest.java
│       │   │   │   └── response/
│       │   │   │       ├── PurchaseOrderResponse.java
│       │   │   │       ├── GRNResponse.java
│       │   │   │       ├── SupplierResponse.java
│       │   │   │       └── VarianceReportResponse.java
│       │   │   │
│       │   │   ├── mapper/
│       │   │   │   ├── PurchaseOrderMapper.java
│       │   │   │   └── SupplierMapper.java
│       │   │   │
│       │   │   ├── exception/
│       │   │   │   ├── PONotFoundException.java
│       │   │   │   ├── InvalidGRNException.java
│       │   │   │   └── SupplierNotFoundException.java
│       │   │   │
│       │   │   ├── enums/
│       │   │   │   ├── POStatus.java
│       │   │   │   ├── ApprovalStatus.java
│       │   │   │   └── GRNStatus.java
│       │   │   │
│       │   │   └── event/
│       │   │       ├── POApprovedEvent.java
│       │   │       ├── GRNPostedEvent.java
│       │   │       └── PurchaseEventPublisher.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-dev.yml
│       │       └── db/
│       │           └── migration/
│       │               ├── V1__create_suppliers_table.sql
│       │               ├── V2__create_purchase_requisition_table.sql
│       │               ├── V3__create_purchase_order_table.sql
│       │               ├── V4__create_grn_table.sql
│       │               ├── V5__create_supplier_invoice_table.sql
│       │               └── V6__create_approval_history_table.sql
│       │
│       └── test/
│           └── java/com/supasoft/purchase/
│               ├── service/
│               │   └── PurchaseOrderServiceTest.java
│               └── integration/
│                   └── GRNIntegrationTest.java
│
│
├── transfer-service/         # Inter-branch Transfer Service
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/transfer/
│       │   │   ├── TransferServiceApplication.java
│       │   │   │
│       │   │   ├── config/
│       │   │   │   ├── DatabaseConfig.java
│       │   │   │   ├── SecurityConfig.java
│       │   │   │   ├── SwaggerConfig.java
│       │   │   │   └── AsyncConfig.java
│       │   │   │
│       │   │   ├── controller/
│       │   │   │   ├── StockTransferController.java
│       │   │   │   ├── TransferRequestController.java
│       │   │   │   ├── BranchController.java
│       │   │   │   └── TransferReportController.java
│       │   │   │
│       │   │   ├── service/
│       │   │   │   ├── TransferService.java
│       │   │   │   ├── TransferServiceImpl.java
│       │   │   │   ├── TransferRequestService.java
│       │   │   │   ├── TransferRequestServiceImpl.java
│       │   │   │   ├── BranchService.java
│       │   │   │   ├── BranchServiceImpl.java
│       │   │   │   └── TransferApprovalService.java
│       │   │   │
│       │   │   ├── repository/
│       │   │   │   ├── StockTransferRepository.java
│       │   │   │   ├── TransferItemRepository.java
│       │   │   │   ├── TransferRequestRepository.java
│       │   │   │   ├── BranchRepository.java
│       │   │   │   └── TransferApprovalRepository.java
│       │   │   │
│       │   │   ├── entity/
│       │   │   │   ├── StockTransfer.java
│       │   │   │   ├── StockTransferItem.java
│       │   │   │   ├── TransferRequest.java
│       │   │   │   ├── TransferRequestItem.java
│       │   │   │   ├── Branch.java
│       │   │   │   └── TransferApproval.java
│       │   │   │
│       │   │   ├── dto/
│       │   │   │   ├── request/
│       │   │   │   │   ├── CreateTransferRequest.java
│       │   │   │   │   ├── ReceiveTransferRequest.java
│       │   │   │   │   ├── ApproveTransferRequest.java
│       │   │   │   │   └── CreateBranchRequest.java
│       │   │   │   └── response/
│       │   │   │       ├── TransferResponse.java
│       │   │   │       ├── TransferStatusResponse.java
│       │   │   │       ├── BranchResponse.java
│       │   │   │       └── TransferVarianceResponse.java
│       │   │   │
│       │   │   ├── mapper/
│       │   │   │   ├── TransferMapper.java
│       │   │   │   └── BranchMapper.java
│       │   │   │
│       │   │   ├── exception/
│       │   │   │   ├── TransferNotFoundException.java
│       │   │   │   ├── InvalidTransferException.java
│       │   │   │   └── BranchNotFoundException.java
│       │   │   │
│       │   │   ├── enums/
│       │   │   │   └── TransferStatus.java
│       │   │   │
│       │   │   └── event/
│       │   │       ├── TransferInitiatedEvent.java
│       │   │       ├── TransferReceivedEvent.java
│       │   │       └── TransferEventPublisher.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-dev.yml
│       │       └── db/
│       │           └── migration/
│       │               ├── V1__create_branches_table.sql
│       │               ├── V2__create_transfer_request_table.sql
│       │               ├── V3__create_stock_transfer_table.sql
│       │               ├── V4__create_transfer_items_table.sql
│       │               └── V5__create_transfer_approval_table.sql
│       │
│       └── test/
│           └── java/com/supasoft/transfer/
│               └── service/
│                   └── TransferServiceTest.java
│
│
├── reports-service/          # Reporting & Analytics Service
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/reports/
│       │   │   ├── ReportsServiceApplication.java
│       │   │   │
│       │   │   ├── config/
│       │   │   │   ├── DatabaseConfig.java
│       │   │   │   ├── SecurityConfig.java
│       │   │   │   ├── SwaggerConfig.java
│       │   │   │   ├── CacheConfig.java
│       │   │   │   └── SchedulingConfig.java
│       │   │   │
│       │   │   ├── controller/
│       │   │   │   ├── SalesReportController.java
│       │   │   │   ├── StockReportController.java
│       │   │   │   ├── PurchaseReportController.java
│       │   │   │   ├── FinancialReportController.java
│       │   │   │   └── DashboardController.java
│       │   │   │
│       │   │   ├── service/
│       │   │   │   ├── SalesReportService.java
│       │   │   │   ├── SalesReportServiceImpl.java
│       │   │   │   ├── StockReportService.java
│       │   │   │   ├── StockReportServiceImpl.java
│       │   │   │   ├── PurchaseReportService.java
│       │   │   │   ├── FinancialReportService.java
│       │   │   │   ├── DashboardService.java
│       │   │   │   ├── ExcelExportService.java
│       │   │   │   ├── PDFExportService.java
│       │   │   │   └── ScheduledReportService.java
│       │   │   │
│       │   │   ├── repository/
│       │   │   │   ├── SalesReportRepository.java
│       │   │   │   ├── StockReportRepository.java
│       │   │   │   ├── PurchaseReportRepository.java
│       │   │   │   └── FinancialReportRepository.java
│       │   │   │
│       │   │   ├── dto/
│       │   │   │   ├── request/
│       │   │   │   │   ├── ReportFilterRequest.java
│       │   │   │   │   ├── DateRangeRequest.java
│       │   │   │   │   └── ExportRequest.java
│       │   │   │   └── response/
│       │   │   │       ├── SalesSummaryReport.java
│       │   │   │       ├── StockValuationReport.java
│       │   │   │       ├── ProfitLossReport.java
│       │   │   │       ├── FastMovingItemsReport.java
│       │   │   │       ├── SlowMovingItemsReport.java
│       │   │   │       ├── ExpiryReport.java
│       │   │   │       ├── BranchPerformanceReport.java
│       │   │   │       └── DashboardSummary.java
│       │   │   │
│       │   │   ├── mapper/
│       │   │   │   └── ReportMapper.java
│       │   │   │
│       │   │   ├── util/
│       │   │   │   ├── ExcelGenerator.java
│       │   │   │   ├── PDFGenerator.java
│       │   │   │   └── ChartGenerator.java
│       │   │   │
│       │   │   └── scheduled/
│       │   │       └── DailyReportScheduler.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-dev.yml
│       │       ├── templates/
│       │       │   ├── sales-report.html
│       │       │   ├── stock-report.html
│       │       │   └── profit-loss-report.html
│       │       │
│       │       └── static/
│       │           └── report-styles.css
│       │
│       └── test/
│           └── java/com/supasoft/reports/
│               └── service/
│                   └── SalesReportServiceTest.java
│
│
├── notification-service/     # Notification Service
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/notification/
│       │   │   ├── NotificationServiceApplication.java
│       │   │   │
│       │   │   ├── config/
│       │   │   │   ├── DatabaseConfig.java
│       │   │   │   ├── MailConfig.java
│       │   │   │   ├── SmsConfig.java
│       │   │   │   └── MessagingConfig.java
│       │   │   │
│       │   │   ├── controller/
│       │   │   │   └── NotificationController.java
│       │   │   │
│       │   │   ├── service/
│       │   │   │   ├── EmailService.java
│       │   │   │   ├── EmailServiceImpl.java
│       │   │   │   ├── SmsService.java
│       │   │   │   ├── SmsServiceImpl.java
│       │   │   │   ├── WhatsAppService.java
│       │   │   │   ├── PushNotificationService.java
│       │   │   │   └── NotificationTemplateService.java
│       │   │   │
│       │   │   ├── repository/
│       │   │   │   ├── NotificationRepository.java
│       │   │   │   └── NotificationTemplateRepository.java
│       │   │   │
│       │   │   ├── entity/
│       │   │   │   ├── Notification.java
│       │   │   │   └── NotificationTemplate.java
│       │   │   │
│       │   │   ├── dto/
│       │   │   │   ├── EmailRequest.java
│       │   │   │   ├── SmsRequest.java
│       │   │   │   └── NotificationResponse.java
│       │   │   │
│       │   │   ├── listener/
│       │   │   │   ├── SalesEventListener.java
│       │   │   │   ├── StockEventListener.java
│       │   │   │   └── TransferEventListener.java
│       │   │   │
│       │   │   └── enums/
│       │   │       ├── NotificationType.java
│       │   │       └── NotificationStatus.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-dev.yml
│       │       ├── db/
│       │       │   └── migration/
│       │       │       ├── V1__create_notifications_table.sql
│       │       │       └── V2__create_notification_templates_table.sql
│       │       │
│       │       └── templates/
│       │           ├── email/
│       │           │   ├── welcome-email.html
│       │           │   ├── invoice-email.html
│       │           │   └── stock-alert-email.html
│       │           └── sms/
│       │               └── sms-templates.properties
│       │
│       └── test/
│           └── java/com/supasoft/notification/
│               └── service/
│                   └── EmailServiceTest.java
│
│
├── integration-service/      # External Integrations Service
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/integration/
│       │   │   ├── IntegrationServiceApplication.java
│       │   │   │
│       │   │   ├── config/
│       │   │   │   ├── MpesaConfig.java
│       │   │   │   ├── EtimsConfig.java
│       │   │   │   ├── RestTemplateConfig.java
│       │   │   │   └── SecurityConfig.java
│       │   │   │
│       │   │   ├── controller/
│       │   │   │   ├── MpesaController.java
│       │   │   │   ├── EtimsController.java
│       │   │   │   └── CallbackController.java
│       │   │   │
│       │   │   ├── service/
│       │   │   │   ├── mpesa/
│       │   │   │   │   ├── MpesaService.java
│       │   │   │   │   ├── MpesaServiceImpl.java
│       │   │   │   │   ├── MpesaStkPushService.java
│       │   │   │   │   ├── MpesaB2CService.java
│       │   │   │   │   └── MpesaCallbackService.java
│       │   │   │   │
│       │   │   │   └── etims/
│       │   │   │       ├── EtimsService.java
│       │   │   │       ├── EtimsServiceImpl.java
│       │   │   │       ├── EtimsInvoiceService.java
│       │   │   │       └── EtimsSyncService.java
│       │   │   │
│       │   │   ├── repository/
│       │   │   │   ├── MpesaTransactionRepository.java
│       │   │   │   └── EtimsLogRepository.java
│       │   │   │
│       │   │   ├── entity/
│       │   │   │   ├── MpesaTransaction.java
│       │   │   │   └── EtimsLog.java
│       │   │   │
│       │   │   ├── dto/
│       │   │   │   ├── mpesa/
│       │   │   │   │   ├── StkPushRequest.java
│       │   │   │   │   ├── StkPushResponse.java
│       │   │   │   │   ├── MpesaCallbackRequest.java
│       │   │   │   │   └── B2CRequest.java
│       │   │   │   │
│       │   │   │   └── etims/
│       │   │   │       ├── EtimsInvoiceRequest.java
│       │   │   │       ├── EtimsInvoiceResponse.java
│       │   │   │       └── EtimsItemRequest.java
│       │   │   │
│       │   │   ├── client/
│       │   │   │   ├── MpesaApiClient.java
│       │   │   │   └── EtimsApiClient.java
│       │   │   │
│       │   │   └── util/
│       │   │       ├── MpesaSecurityUtil.java
│       │   │       └── EtimsSignatureUtil.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-dev.yml
│       │       └── db/
│       │           └── migration/
│       │               ├── V1__create_mpesa_transactions_table.sql
│       │               └── V2__create_etims_log_table.sql
│       │
│       └── test/
│           └── java/com/supasoft/integration/
│               └── service/
│                   └── MpesaServiceTest.java
│
│
├── api-gateway/              # API Gateway
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/supasoft/gateway/
│       │   │   ├── ApiGatewayApplication.java
│       │   │   │
│       │   │   ├── config/
│       │   │   │   ├── GatewayConfig.java
│       │   │   │   ├── SecurityConfig.java
│       │   │   │   ├── CorsConfig.java
│       │   │   │   └── RateLimitConfig.java
│       │   │   │
│       │   │   ├── filter/
│       │   │   │   ├── JwtAuthenticationFilter.java
│       │   │   │   ├── LoggingFilter.java
│       │   │   │   └── RateLimitFilter.java
│       │   │   │
│       │   │   └── util/
│       │   │       └── JwtUtil.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml
│       │       └── application-dev.yml
│       │
│       └── test/
│           └── java/com/supasoft/gateway/
│               └── ApiGatewayApplicationTests.java
│
│
└── audit-service/            # Audit Trail Service (Optional)
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/com/supasoft/audit/
        │   │   ├── AuditServiceApplication.java
        │   │   │
        │   │   ├── config/
        │   │   │   ├── DatabaseConfig.java
        │   │   │   └── MessagingConfig.java
        │   │   │
        │   │   ├── listener/
        │   │   │   └── AuditEventListener.java
        │   │   │
        │   │   ├── service/
        │   │   │   ├── AuditService.java
        │   │   │   └── AuditServiceImpl.java
        │   │   │
        │   │   ├── repository/
        │   │   │   └── AuditLogRepository.java
        │   │   │
        │   │   ├── entity/
        │   │   │   └── AuditLog.java
        │   │   │
        │   │   └── enums/
        │   │       ├── AuditAction.java
        │   │       └── AuditModule.java
        │   │
        │   └── resources/
        │       ├── application.yml
        │       └── db/
        │           └── migration/
        │               └── V1__create_audit_log_table.sql
        │
        └── test/
            └── java/com/supasoft/audit/
                └── service/
                    └── AuditServiceTest.java
