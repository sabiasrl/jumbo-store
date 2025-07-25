# Jumbo Stores Finder

A Spring Boot application that finds the closest Jumbo stores based on geographic coordinates using PostGIS for spatial queries.

## Architecture

This project follows the **Hexagonal Architecture** (Ports and Adapters) design patterns:

```
src/main/java/com/jumbo/stores/
├── application/           # Application layer (services, use cases, ports)
│   ├── port/
│   │   ├── in/           # Incoming ports (interfaces called by controllers)
│   │   │   └── FindClosestStoresUseCase.java
│   │   └── out/          # Outgoing ports (interfaces implemented by adapters)
│   │       └── StoreRepository.java
│   └── service/          # Application services (implement in-ports)
│       └── StoreService.java
├── domain/               # Domain layer (business logic, entities, value objects)
│   ├── model/           # Domain models
│   │   └── Store.java
│   └── service/         # Pure domain services
│       └── DistanceCalculator.java
├── adapter/             # Adapters (implement in/out ports)
│   ├── in/
│   │   └── web/         # REST controllers (Spring Web)
│   │       ├── StoreController.java
│   │       ├── dto/
│   │       │   └── StoreDto.java
│   │       └── exception/
│   │           └── ApiExceptionHandler.java
│   └── out/
│       └── persistence/ # JPA repositories, MongoDB, etc.
│           ├── StoreJpaEntity.java
│           ├── StoreJpaRepository.java
│           └── StorePersistenceAdapter.java
└── JumboStoresFinderApplication.java
```

## Layer Responsibilities

### 1. Domain Layer (`domain/`)
- **Purpose**: Contains the core business logic and rules
- **Components**:
  - `Store`: Domain entity representing a store
  - `DistanceCalculator`: Pure domain service for distance calculations
- **Characteristics**: 
  - No dependencies on external frameworks
  - Pure business logic
  - Framework-agnostic

### 2. Application Layer (`application/`)
- **Purpose**: Orchestrates use cases and coordinates between domain and adapters
- **Components**:
  - **Ports**: Define contracts for incoming and outgoing dependencies
  - **Services**: Implement use cases and coordinate domain objects
- **Characteristics**:
  - Contains use case implementations
  - Depends only on domain layer
  - Defines ports (interfaces) for adapters

### 3. Adapter Layer (`adapter/`)
- **Purpose**: Implements ports to interact with external systems
- **Components**:
  - **In Adapters**: Controllers, CLI, etc.
  - **Out Adapters**: Database repositories, external APIs, etc.
- **Characteristics**:
  - Implements ports defined in application layer
  - Handles framework-specific concerns
  - Converts between domain objects and external formats


## Features

- **Spatial Queries**: Uses PostGIS for efficient geographic distance calculations
- **Hexagonal Architecture**: Clean separation of concerns with ports and adapters
- **REST API**: Provides a simple REST endpoint to find closest stores
- **Validation**: Input validation for coordinates
- **Documentation**: OpenAPI/Swagger documentation
- **Monitoring**: Spring Boot Actuator for health checks
- **Testing**: Comprehensive test suite with unit, integration, and E2E tests

## API Endpoints

### Find Closest Stores

```
GET /stores?latitude={lat}&longitude={lng}
```

**Parameters:**
- `latitude` (required): Latitude coordinate (-90.0 to 90.0)
- `longitude` (required): Longitude coordinate (-180.0 to 180.0)

**Response:**
```json
[
  {
    "addressName": "Jumbo Store Name",
    "latitude": 52.123456,
    "longitude": 5.123456,
    "distance": 1.234567
  }
]
```

## Quick Start

### Prerequisites

- Java 21
- Docker and Docker Compose
- Maven

### Running with Docker Compose

```bash
# Start the application with PostGIS
docker compose up -d --build

# Test the API
curl "http://localhost:8080/stores?latitude=51.6167&longitude=5.5486"
```

### Running Locally

```bash
# Build the project
./mvnw clean compile

# Run tests
./mvnw test

# Start PostGIS (alternative to Docker Compose)
docker run --name jumbo-postgres \
  -e POSTGRES_DB=jumbo \
  -e POSTGRES_USER=jumbo \
  -e POSTGRES_PASSWORD=jumbo \
  -p 5432:5432 \
  -d postgis/postgis:16-3.4

# Run the application
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local"
```

## Development

### Project Structure

The project follows hexagonal architecture principles:

- **Domain Layer**: Contains business logic and entities
- **Application Layer**: Orchestrates use cases and defines ports
- **Adapter Layer**: Implements ports for external systems

### Testing

```bash
# Run all tests
./mvnw test
```

Tests use Testcontainers with PostGIS to ensure they run against the same database technology as production.

### Code Coverage

To generate a code coverage report, run the tests and then open the following file in your browser:

`target/site/jacoco/index.html`

### Code Formatting

To format the code and organize the imports, run the following command:

```bash
./mvnw formatter:format
```

## Docker

### Building the Application Image

To build the Docker image manually:

```bash
cd jumbo-stores-finder
docker build -t jumbo-stores-finder .
```

### Running with Docker Compose

The recommended way to run the application is with Docker Compose:

```bash
# Start all services
docker compose up --build

# Run in background
docker compose up --build -d

# Stop all services
docker compose down

# View logs
docker compose logs jumbo-stores-finder
```

### Manual Docker Run (requires external PostgreSQL)

If you want to run the container manually (not recommended):

```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/jumbo \
  -e SPRING_DATASOURCE_USERNAME=jumbo \
  -e SPRING_DATASOURCE_PASSWORD=jumbo \
  jumbo-stores-finder
``` 