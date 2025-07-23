# Jumbo Stores Finder

This is a simple Spring Boot application that provides a REST API to find the 5 closest Jumbo stores to a given location.

## How to run the application

1.  Make sure you have Java 21 and Maven installed.
2.  Navigate to the `jumbo-stores-finder` directory.
3.  Run the application using the following command:
    ```
    ./mvnw spring-boot:run
    ```
4.  The application will start on port 8080.

## API Usage

To find the 5 closest stores, you can send a GET request to the `/stores` endpoint with the `latitude` and `longitude` query parameters.

**Example:**

```bash
curl "http://localhost:8080/stores?latitude=52.3676&longitude=4.9041"
```

## API Documentation

The OpenAPI specification for this API is available at [/v3/api-docs](http://localhost:8080/v3/api-docs).

**Example:**

```bash
curl http://localhost:8080/v3/api-docs
```

You can also interact with the API using the Swagger UI at [/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

## H2 Console

The H2 in-memory database console is enabled. You can access it at http://localhost:8080/h2-console.

- **JDBC URL:** jdbc:h2:mem:testdb
- **User Name:** sa
- **Password:** (leave blank)

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