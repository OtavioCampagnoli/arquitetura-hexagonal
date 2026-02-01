# Arquitetura Hexagonal - Product Service

## Purpose
Small Java/Maven demo showing how to structure an application using the Hexagonal (Ports and Adapters) architecture so business rules stay isolated from infrastructure. This repository implements a `Product` domain, a service port with business rules, and a JPA persistence adapter.

## Core Concepts

- Hexagonal Architecture (Ports & Adapters)
    - Separates the application core (domain + use cases) from external concerns (DB, web, messaging).
    - The core exposes *ports* (interfaces) describing required and provided behavior.
    - *Adapters* implement ports to interact with specific technologies (JPA, REST, CLI).
    - Dependency inversion: core depends on interfaces only; adapters depend on those interfaces and are injected.

- Ports
    - Incoming ports: use-case interfaces called by adapters (e.g., application services).
    - Outgoing ports: interfaces the core uses to access external systems (e.g., persistence).

- Adapters
    - Translate between external representations (entities, DTOs) and domain models.
    - Keep minimal logic: mapping + delegation to domain/service.

- Why this helps
    - Testability: replace adapters with mocks in unit tests.
    - Replaceable infrastructure: swap DB, messaging, or transport without changing core.
    - Clear separation of business rules from framework code.

## How this project maps to the pattern

- Application core (packages under `com.ocampagnoli.arquitetura_hexagonal.application.product`)
    - `Product` / `IProduct`: domain model / interface.
    - `IProductService`: business rules and use cases.
    - `IProductPersistence`: outgoing persistence port.

- Persistence adapter (`com.ocampagnoli.arquitetura_hexagonal.adapters.persistence.jpa.product`)
    - `ProductPersistenceAdapter` implements `IProductPersistence` using a JPA repository (`IProductRepository`).
    - `ProductEntity` handles mapping between DB and domain (`toEntity` / `toDomain`).

- Tests
    - Adapter tests mock the repository and static mapping methods to validate adapter behavior and repository interactions.
    - Services tests should mock outgoing ports to verify business rules without touching infra.

## Typical flows (examples)
- Get product:
    - Adapter parses incoming id, finds entity via repository, converts to domain and returns.
- Save product:
    - Adapter maps domain to entity, saves via repository, maps saved entity back to domain.
- Enable/disable:
    - Adapter loads entity, applies domain changes via service/domain, persists and returns domain view.

## Testing notes
- Unit tests use JUnit 5 + Mockito.
- For mocking static mapper methods (`ProductEntity.toDomain` / `toEntity`) add `mockito-inline` (or use separate mapper bean to avoid static mocking).
- Example commands:
    - Build: `mvn clean package`
    - Test: `mvn test`

## Useful links to learn more
- Alistair Cockburn — Hexagonal Architecture (original notes): https://alistair.cockburn.us/hexagonal-architecture
- Testing and Mockito
    - Mockito static mocking and inline mock maker: https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#static_mocks
    - Mockito inline Maven artifact: `org.mockito:mockito-inline` (use a recent 4.x version)

## Practical tips
- Keep mappers isolated (avoid static methods if you prefer easier testing).
- Keep adapters thin: mapping + delegation.
- Put all business rules in services/domain and test them independently.
- Start with unit tests around the core, then add integration tests for adapters.
