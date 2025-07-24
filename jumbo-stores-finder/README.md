# Jumbo Stores Finder

This is a simple Spring Boot application that provides a REST API to find the 5 closest Jumbo stores to a given location using PostGIS spatial functions.

## How to run the application

### Prerequisites

1. Make sure you have Java 21 and Maven installed.
2. Install PostgreSQL with PostGIS extension:

   **Using Docker (recommended):**
   ```bash
   docker run --name jumbo-postgres \
     -e POSTGRES_DB=jumbo \
     -e POSTGRES_USER=jumbo \
     -e POSTGRES_PASSWORD=jumbo \
     -p 5432:5432 \
     -d postgis/postgis:16-3.4
   ```

   **Using local PostgreSQL:**
   - Install PostgreSQL and PostGIS extension
   - Create database: `CREATE DATABASE jumbo;`
   - Create user: `CREATE USER jumbo WITH PASSWORD 'jumbo';`
   - Grant privileges: `GRANT ALL PRIVILEGES ON DATABASE jumbo TO jumbo;`
   - Enable PostGIS: `CREATE EXTENSION postgis;`

### Running the application

1. Navigate to the `jumbo-stores-finder` directory.
2. Run the application using the following command:
   ```
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```
3. The application will start on port 8080.

**Note:** The application requires a PostgreSQL database with PostGIS extension. For development and testing, the application uses Testcontainers to automatically spin up a PostGIS instance.

## API Usage

To find the 5 closest stores, you can send a GET request to the `/stores` endpoint with the `latitude` and `longitude` query parameters.

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

To build the Docker image, run the following command from the `jumbo-stores-finder` directory:

```bash
docker build -t jumbo-stores-finder .
```

To run the container:

```bash
docker run -p 8080:8080 jumbo-stores-finder
``` 