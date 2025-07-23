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

Example:

```
curl "http://localhost:8080/stores?latitude=52.3676&longitude=4.9041"
```

## H2 Console

The H2 in-memory database console is enabled. You can access it at http://localhost:8080/h2-console.

- **JDBC URL:** jdbc:h2:mem:testdb
- **User Name:** sa
- **Password:** (leave blank)

## Development

### Running Tests

To run the unit tests, use the following command:

```
./mvnw test
```

### Code Coverage

The project uses the JaCoCo plugin to generate a code coverage report. After running the tests, you can find the report in `target/site/jacoco/index.html`.

### Code Formatting

The project uses the `formatter-maven-plugin` to format the code. To format the code, run the following command:

```
./mvnw formatter:format
``` 