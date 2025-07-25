# Jumbo Stores Finder

This is a simple Spring Boot application that provides a REST API to find the 5 closest Jumbo stores to a given location using PostGIS spatial functions.

## Quick Start with Docker Compose

The easiest way to run the application is using Docker Compose, which automatically sets up PostgreSQL with PostGIS:

```bash
docker-compose up --build
```

This will:
- Start a PostgreSQL database with PostGIS extension
- Build and run the Spring Boot application
- Make the API available at http://localhost:8080

## Local Development Setup

### Prerequisites

1. Make sure you have Java 21 and Maven installed.
2. Install PostgreSQL with PostGIS extension:

   **Using Docker (recommended for local development):**
   ```bash
   docker run --name jumbo-postgres \
     -e POSTGRES_DB=jumbo \
     -e POSTGRES_USER=jumbo \
     -e POSTGRES_PASSWORD=jumbo \
     -p 5432:5432 \
     -d postgis/postgis:16-3.4
   ```

   **Using local PostgreSQL installation:**
   - Install PostgreSQL and PostGIS extension
   - Create database: `CREATE DATABASE jumbo;`
   - Create user: `CREATE USER jumbo WITH PASSWORD 'jumbo';`
   - Grant privileges: `GRANT ALL PRIVILEGES ON DATABASE jumbo TO jumbo;`
   - Enable PostGIS: `CREATE EXTENSION postgis;`

### Running the application locally

1. Navigate to the `jumbo-stores-finder` directory.
2. Run the application with the local profile:
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run -Dspring.profiles.active=local
   ```
3. The application will start on port 8080.

## Configuration Profiles

The application supports different configuration profiles:

- **Default profile**: Uses `postgis` hostname (for Docker Compose)
- **Local profile**: Uses `localhost` hostname (for local development)

### Environment Variables (Docker Compose)

When running with Docker Compose, the following environment variables are automatically set:

```yaml
SPRING_DATASOURCE_URL: jdbc:postgresql://postgis:5432/jumbo
SPRING_DATASOURCE_USERNAME: jumbo
SPRING_DATASOURCE_PASSWORD: jumbo
SPRING_DATASOURCE_DRIVER_CLASS_NAME: org.postgresql.Driver
```

## API Usage

To find the 5 closest stores, send a GET request to the `/stores` endpoint with `latitude` and `longitude` query parameters.

**Example:**

```bash
curl "http://localhost:8080/stores?latitude=51.6167&longitude=5.5486"
```

## API Documentation

The OpenAPI specification for this API is available at [/v3/api-docs](http://localhost:8080/v3/api-docs).

**Example:**

```bash
curl http://localhost:8080/v3/api-docs
```

You can also interact with the API using the Swagger UI at [/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

## Database

This application uses PostgreSQL with PostGIS extension for spatial queries. The application leverages:

- **PostGIS spatial functions**: `ST_DistanceSphere` for efficient proximity calculations
- **Geometry data types**: `GEOMETRY(Point,4326)` for storing store locations
- **Real spatial data**: All 587 Jumbo store locations from the Netherlands

### Database Health Check

The Docker Compose configuration includes a health check for PostgreSQL to ensure the database is ready before starting the application:

```yaml
healthcheck:
  test: ["CMD-SHELL", "pg_isready -U jumbo -d jumbo"]
  interval: 30s
  timeout: 10s
  retries: 5
```

## Actuator Endpoints

The Spring Boot Actuator is enabled and all endpoints are exposed. Here are some of the most useful endpoints:

- **Health:** [/actuator/health](http://localhost:8080/actuator/health)
- **Info:** [/actuator/info](http://localhost:8080/actuator/info)
- **Metrics:** [/actuator/metrics](http://localhost:8080/actuator/metrics)
- **Beans:** [/actuator/beans](http://localhost:8080/actuator/beans)
- **Mappings:** [/actuator/mappings](http://localhost:8080/actuator/mappings)

**Examples:**

```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/info
```

## Development

### Running Tests

To run the unit tests, use the following command:

```bash
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
docker-compose up --build

# Run in background
docker-compose up --build -d

# Stop all services
docker-compose down

# View logs
docker-compose logs jumbo-stores-finder
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