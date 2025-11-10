# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Splearn** is a Spring Boot learning platform implementing **Hexagonal Architecture (Ports and Adapters)** with **Domain-Driven Design** principles. The codebase follows Toby's clean architecture patterns with clear separation between domain logic, application services, and infrastructure adapters.

## Architecture

### Hexagonal Architecture Structure

```
tobyspring.splearn/
├── domain/              # Core business logic (entities, value objects, domain services)
│   ├── member/         # Member domain model
│   └── shared/         # Shared value objects (Email, etc.)
├── application/        # Use cases and application services
│   └── member/
│       ├── provided/   # Inbound ports (interfaces for what the app provides)
│       └── required/   # Outbound ports (interfaces for what the app requires)
└── adapter/            # Infrastructure implementations
    ├── integration/    # External integrations (email, etc.)
    ├── security/       # Security adapters (password encoding)
    ├── persistence/    # Database adapters
    └── webapi/         # REST API controllers
```

### Key Architectural Patterns

1. **Domain Layer** (`domain/`):
   - Contains pure business logic with no framework dependencies
   - Entities extend `AbstractEntity` with ID field
   - Uses factory methods for entity creation (e.g., `Member.register()`)
   - State transitions enforced through domain methods (e.g., `activate()`, `deactivate()`)
   - JPA mappings are externalized to `src/main/resources/META-INF/orm.xml`

2. **Application Layer** (`application/`):
   - **Provided Ports** (`provided/`): Interfaces defining what services the application offers (e.g., `MemberRegister`, `MemberFinder`)
   - **Required Ports** (`required/`): Interfaces defining what the application needs from infrastructure (e.g., `MemberRepository`, `EmailSender`)
   - Service implementations use `@Service` and implement provided ports
   - All services use `@Transactional` and `@Validated`

3. **Adapter Layer** (`adapter/`):
   - Implements the required ports from the application layer
   - Uses Spring Data JPA for `MemberRepository`
   - External integrations like `DummyEmailSender` implement `EmailSender`
   - Security adapters like `SecurePasswordEncoder` implement domain's `PasswordEncoder`

4. **Dependency Rule**: Dependencies flow inward only
   - Domain has no dependencies on outer layers
   - Application depends only on domain
   - Adapters depend on application and domain

### Domain Model Details

#### Member Entity
- **Natural ID**: `Email` (unique business identifier)
- **States**: `PENDING` → `ACTIVE` → `DEACTIVATED`
- **Relationships**: 1:1 with `MemberDetail` (cascade all)
- **Key behaviors**: `register()`, `activate()`, `deactivate()`, `verifyPassword()`, `changeNickname()`, `changePassword()`

#### Value Objects
- `Email`: Embeddable with validation
- Immutable design pattern

#### JPA Mapping
- All JPA mappings are in `META-INF/orm.xml` (not annotations)
- Uses Hibernate's Natural ID cache for `Email`
- Field access strategy (not property access)

## Common Commands

### Build and Run
```bash
./gradlew build          # Build the project
./gradlew bootRun        # Run the Spring Boot application
./gradlew clean build    # Clean and rebuild
```

### Testing
```bash
./gradlew test                                          # Run all tests
./gradlew test --tests "ClassName.methodName"          # Run specific test
./gradlew test --tests "tobyspring.splearn.domain.*"   # Run domain tests
./gradlew test --rerun-tasks                            # Force re-run all tests
```

### Code Quality
```bash
./gradlew spotbugsMain   # Run SpotBugs on main source
./gradlew spotbugsTest   # Run SpotBugs on test source
./gradlew check          # Run all checks (tests + SpotBugs)
```

## Java 21 Configuration Issue

**Known Issue**: Tests and bootRun fail with `InaccessibleObjectException` due to Java 21's stricter reflection access.

**Solution**: The `build.gradle.kts` file needs JVM arguments to allow Hibernate/Lombok reflection access:
```kotlin
tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs(
        "--add-opens", "java.base/java.lang=ALL-UNNAMED",
        "--add-opens", "java.base/java.util=ALL-UNNAMED"
    )
}
```

## Development Guidelines

### When Adding New Features

1. **Start with Domain**: Define entities, value objects, and domain logic first
2. **Define Ports**: Create interfaces in `application/[feature]/provided` and `required`
3. **Implement Services**: Create service classes implementing provided ports
4. **Add Adapters**: Implement required ports in the adapter layer
5. **Update ORM Mapping**: If adding entities, update `META-INF/orm.xml`

### Domain Entity Rules

- Use `protected` no-args constructor for JPA
- Use static factory methods for entity creation
- Validate state transitions with `Assert.state()`
- Use `@NaturalId` for business keys
- Keep entity fields `private` and provide getters only where needed

### Application Service Rules

- All services implement interfaces from `application/[feature]/provided`
- Use constructor injection with `@RequiredArgsConstructor`
- Apply `@Transactional`, `@Validated`, and `@Service` annotations
- Depend on required ports (interfaces), not implementations
- Use `@Valid` on method parameters for request validation

### Testing Patterns

- Domain tests are pure unit tests (no Spring context)
- Application tests use `@SpringBootTest` for integration testing
- Use `MemberFixture` for test data creation
- Repository tests verify both success and constraint violation cases

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.5.7
- **JPA/Hibernate**: Via Spring Data JPA
- **Database**: H2 (dev), MySQL (production)
- **Lombok**: For boilerplate reduction
- **JUnit 5**: Testing framework
- **SpotBugs**: Static analysis

## Package-level Null Safety

Both `domain` and `application` packages use `@NonNullApi` at package level (see `package-info.java`), making all parameters and return values non-null by default unless explicitly annotated with `@Nullable`.
