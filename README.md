# Jumbo Stores Finder

## Assignment

For the assignment details, please refer to the [ASSIGNMENT.md](ASSIGNMENT.md) file.

## Application Documentation

For detailed information about the application, how to run it, and the available endpoints, please refer to the [application's README file](jumbo-stores-finder/README.md).

## How to run the application

```bash
docker-compose up -d --build
```
then verify the Veghel closest shops 
```bash
curl "http://localhost:8080/stores?latitude=51.6167&longitude=5.5486"
```

## End-to-End Testing with CI Verify Script

This project includes an end-to-end testing setup orchestrated with Docker Compose. A `ci-verify.sh` script is provided to automate the testing process.

### What it does

The `ci-verify.sh` script will:

1.  Build the Docker images for the application (`jumbo-stores-finder`) and the end-to-end tests (`e2e`).
2.  Start the services using `docker-compose`.
3.  Run the Maven tests inside the `e2e` container.
4.  If the tests pass, it will generate an HTML test report and print its content to the console. The test reports will also be available in the `reports` directory.
5.  Shut down and remove all the containers after the tests are finished.

### How to run

To run the end-to-end tests, execute the following command from the root of the project:

```bash
./ci-verify.sh
```

### Test Reports

The script will mount a `reports` directory in the project root. After a successful test run, you can find:

-   XML Surefire reports in `reports/surefire-reports`.
-   HTML Surefire report in `reports/site/surefire-report.html`. You can open this file in a browser for a better view of the test results. 