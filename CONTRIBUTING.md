# Contributing to Supasoft

Thank you for your interest in contributing to Supasoft - Supermarket Management System! We welcome contributions from the community and are grateful for your support.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Workflow](#development-workflow)
- [Coding Standards](#coding-standards)
- [Commit Guidelines](#commit-guidelines)
- [Pull Request Process](#pull-request-process)
- [Testing](#testing)
- [Documentation](#documentation)
- [Community](#community)

---

## Code of Conduct

By participating in this project, you agree to abide by our Code of Conduct:

- **Be respectful**: Treat everyone with respect. Healthy debate is encouraged, but be professional.
- **Be inclusive**: We welcome contributors from all backgrounds and skill levels.
- **Be collaborative**: Work together to improve the project for everyone.
- **Be constructive**: Provide helpful feedback and be open to receiving it.

Report any unacceptable behavior to: conduct@supasoft.com

---

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java 21** (OpenJDK or Oracle JDK)
- **Maven 3.8+**
- **Docker & Docker Compose**
- **Git**
- **MySQL 8.0+** (for local development)
- **IntelliJ IDEA** or **VS Code** (recommended IDEs)

### Fork and Clone

1. Fork the repository on GitLab/GitHub
2. Clone your fork:
   ```bash
   git clone https://gitlab.com/your-username/supasoft-api.git
   cd supasoft-api
   ```

3. Add upstream remote:
   ```bash
   git remote add upstream https://gitlab.com/supasoft/supasoft-api.git
   ```

### Setup Development Environment

Run the automated setup script:

```bash
cd scripts
chmod +x setup-local-env.sh
./setup-local-env.sh
```

Or manually:

```bash
# Start infrastructure
docker-compose up -d mysql redis rabbitmq

# Build project
mvn clean install -DskipTests

# Run tests
mvn test
```

### Project Structure

```
api/
├── common/                 # Shared libraries
├── discovery/              # Service discovery (Eureka)
├── api-gateway/           # API Gateway
├── auth-service/          # Authentication service
├── item-service/          # Item management
├── pricing-service/       # Pricing management
├── stock-service/         # Stock management
├── sales-service/         # Sales & POS
├── purchase-service/      # Procurement
├── transfer-service/      # Inter-branch transfers
├── reports-service/       # Reporting & analytics
├── notification-service/  # Notifications
└── integration-service/   # External integrations
```

---

## Development Workflow

### Branching Strategy

We follow **Git Flow**:

- `main` - Production-ready code
- `develop` - Development branch (default)
- `feature/*` - New features
- `bugfix/*` - Bug fixes
- `hotfix/*` - Production hotfixes
- `release/*` - Release preparation

### Creating a Feature Branch

```bash
# Update develop branch
git checkout develop
git pull upstream develop

# Create feature branch
git checkout -b feature/your-feature-name

# Make your changes...

# Push to your fork
git push origin feature/your-feature-name
```

### Branch Naming Convention

- Features: `feature/add-barcode-scanner`
- Bugfixes: `bugfix/fix-stock-calculation`
- Hotfixes: `hotfix/critical-payment-bug`
- Releases: `release/v1.2.0`

---

## Coding Standards

### Java Code Style

We follow **Google Java Style Guide** with some modifications:

#### Formatting

- **Indentation**: 4 spaces (not tabs)
- **Line length**: Maximum 120 characters
- **Braces**: Use K&R style (opening brace on same line)

```java
// Good
public class ItemService {
    public void createItem(ItemRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        // ...
    }
}

// Bad
public class ItemService 
{
    public void createItem(ItemRequest request) 
    {
      if(request==null){throw new IllegalArgumentException("Request cannot be null");}
      // ...
    }
}
```

#### Naming Conventions

- **Classes**: `PascalCase` (e.g., `ItemService`, `StockController`)
- **Methods**: `camelCase` (e.g., `createItem()`, `calculateTotalPrice()`)
- **Variables**: `camelCase` (e.g., `itemId`, `totalAmount`)
- **Constants**: `UPPER_SNAKE_CASE` (e.g., `MAX_RETRY_COUNT`, `DEFAULT_TIMEOUT`)
- **Packages**: `lowercase` (e.g., `com.supasoft.item.service`)

#### Code Organization

```java
@Service
@Transactional
@Slf4j
public class ItemServiceImpl implements ItemService {
    
    // 1. Constants
    private static final int MAX_ITEMS_PER_PAGE = 100;
    
    // 2. Dependencies (constructor injection preferred)
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    
    // 3. Constructor
    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }
    
    // 4. Public methods
    @Override
    public ItemResponse createItem(CreateItemRequest request) {
        // Implementation
    }
    
    // 5. Private methods
    private void validateItem(Item item) {
        // Validation logic
    }
}
```

### Documentation

- **All public classes** must have JavaDoc
- **All public methods** must have JavaDoc
- **Complex logic** should have inline comments

```java
/**
 * Service for managing supermarket items including CRUD operations,
 * barcode generation, and category management.
 * 
 * @author Supasoft Team
 * @since 1.0.0
 */
@Service
public class ItemService {
    
    /**
     * Creates a new item in the system with automatic barcode generation.
     * 
     * @param request the item creation request containing item details
     * @return the created item with generated ID and barcode
     * @throws DuplicateBarcodeException if the barcode already exists
     * @throws ValidationException if the request validation fails
     */
    public ItemResponse createItem(CreateItemRequest request) {
        // Implementation
    }
}
```

### Exception Handling

- Use custom exceptions for business logic errors
- Never catch generic `Exception` unless absolutely necessary
- Always log exceptions with context

```java
// Good
try {
    Item item = itemRepository.findById(itemId)
        .orElseThrow(() -> new ItemNotFoundException(itemId));
} catch (ItemNotFoundException e) {
    log.error("Item not found: {}", itemId, e);
    throw e;
}

// Bad
try {
    Item item = itemRepository.findById(itemId).get();
} catch (Exception e) {
    log.error("Error");
}
```

### Testing

- Write unit tests for all service classes (target: 80%+ coverage)
- Write integration tests for critical flows
- Use meaningful test names

```java
@Test
@DisplayName("Should create item with generated barcode when barcode is not provided")
void shouldCreateItemWithGeneratedBarcode() {
    // Given
    CreateItemRequest request = new CreateItemRequest();
    request.setItemName("Test Item");
    // request.setBarcode() not called
    
    // When
    ItemResponse response = itemService.createItem(request);
    
    // Then
    assertThat(response.getBarcode()).isNotNull();
    assertThat(response.getBarcode()).matches("\\d{13}"); // EAN-13 format
}
```

---

## Commit Guidelines

### Commit Message Format

We follow **Conventional Commits** specification:

```
<type>(<scope>): <subject>

<body>

<footer>
```

#### Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, no logic change)
- `refactor`: Code refactoring
- `perf`: Performance improvements
- `test`: Adding or updating tests
- `chore`: Build process, dependencies, tooling
- `ci`: CI/CD changes

#### Examples

```bash
feat(item): add barcode generation for new items

- Implement EAN-13 barcode generation
- Add validation for existing barcodes
- Update item creation flow

Closes #123
```

```bash
fix(stock): correct weighted average cost calculation

The previous calculation didn't account for returns.
This fix includes return quantities in the average cost formula.

Fixes #456
```

```bash
docs(api): update item service API documentation

Add examples for bulk item creation and barcode scanning endpoints.
```

### Commit Best Practices

- Write clear, concise commit messages
- Commit logical units of work
- Reference issues/tickets in commit messages
- Keep commits focused (one logical change per commit)

---

## Pull Request Process

### Before Submitting

1. **Update your branch** with latest develop:
   ```bash
   git checkout develop
   git pull upstream develop
   git checkout feature/your-feature
   git rebase develop
   ```

2. **Run tests**:
   ```bash
   mvn clean test
   mvn verify
   ```

3. **Check code quality**:
   ```bash
   mvn checkstyle:check
   mvn pmd:check
   ```

4. **Update documentation** if needed

### Submitting Pull Request

1. Push your branch to your fork
2. Create a Pull Request from your fork to `upstream/develop`
3. Fill out the PR template completely
4. Link related issues

### PR Template

```markdown
## Description
Brief description of the changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Related Issues
Closes #123

## Changes Made
- Added barcode generation feature
- Updated item creation flow
- Added unit tests

## Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Manual testing completed

## Checklist
- [ ] Code follows project style guidelines
- [ ] Self-review completed
- [ ] Comments added for complex logic
- [ ] Documentation updated
- [ ] No new warnings introduced
- [ ] Tests added/updated
- [ ] All tests passing
```

### Review Process

- At least **1 approval** required from maintainers
- All **CI checks** must pass
- **No merge conflicts**
- **Code review feedback** addressed

### After Merge

- Delete your feature branch:
  ```bash
  git branch -d feature/your-feature
  git push origin --delete feature/your-feature
  ```

---

## Testing

### Unit Tests

- Located in `src/test/java`
- Use **JUnit 5** and **Mockito**
- Aim for **80%+ code coverage**

```java
@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    
    @Mock
    private ItemRepository itemRepository;
    
    @InjectMocks
    private ItemServiceImpl itemService;
    
    @Test
    void testCreateItem() {
        // Test implementation
    }
}
```

### Integration Tests

- Use **TestContainers** for database
- Test complete flows

```java
@SpringBootTest
@Testcontainers
class ItemIntegrationTest {
    
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");
    
    @Test
    void testItemCreationFlow() {
        // Integration test
    }
}
```

### Running Tests

```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=ItemServiceTest

# Integration tests only
mvn verify -DskipUnitTests=true

# With coverage
mvn test jacoco:report
```

---

## Documentation

### API Documentation

We use **SpringDoc OpenAPI (Swagger)**:

```java
@Operation(
    summary = "Create new item",
    description = "Creates a new item with automatic barcode generation"
)
@ApiResponses({
    @ApiResponse(responseCode = "201", description = "Item created successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "409", description = "Duplicate barcode")
})
@PostMapping
public ResponseEntity<ItemResponse> createItem(
    @Valid @RequestBody CreateItemRequest request) {
    // Implementation
}
```

### README Updates

Update README.md when:
- Adding new features
- Changing setup process
- Updating dependencies
- Changing API contracts

### Architecture Documentation

Update `docs/architecture/` when:
- Adding new services
- Changing data models
- Modifying API contracts
- Updating system architecture

---

## Community

### Getting Help

- **Documentation**: Check `docs/` folder
- **Issues**: Search existing issues before creating new ones
- **Discussions**: Use GitLab/GitHub Discussions
- **Email**: dev@supasoft.com

### Reporting Bugs

Use the **Bug Report** template:

```markdown
**Describe the bug**
Clear description of the bug

**To Reproduce**
Steps to reproduce:
1. Go to '...'
2. Click on '...'
3. See error

**Expected behavior**
What should happen

**Actual behavior**
What actually happens

**Screenshots**
If applicable

**Environment**
- OS: Ubuntu 22.04
- Java: 21
- Browser: Chrome 120

**Additional context**
Any other relevant information
```

### Feature Requests

Use the **Feature Request** template:

```markdown
**Is your feature request related to a problem?**
Clear description of the problem

**Describe the solution you'd like**
What you want to happen

**Describe alternatives you've considered**
Other solutions considered

**Additional context**
Screenshots, mockups, examples
```

---

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

---

## Recognition

Contributors will be:
- Listed in `CONTRIBUTORS.md`
- Mentioned in release notes
- Recognized in project documentation

---

## Questions?

If you have any questions about contributing, please:
- Open a discussion
- Email: contribute@supasoft.com
- Join our community chat

---

**Thank you for contributing to Supasoft! 🚀**

