# Jumbo Stores Finder

[![Build Status](https://dev.azure.com/sabiasrl/jumbo-store-finder/_apis/build/status%2Fsabiasrl.jumbo-store?branchName=main)](https://dev.azure.com/sabiasrl/jumbo-store-finder/_build/latest?definitionId=3&branchName=main)

## Assignment

For the assignment details, please refer to the [ASSIGNMENT.md](ASSIGNMENT.md) file.

## Application Documentation

For detailed information about the application, how to run it, and the available endpoints, please refer to the [application's README file](jumbo-stores-finder/README.md).

## How to run the application

### Quick Start with Docker Compose

```bash
docker-compose up -d --build
```

### Verify the Veghel closest shops

```bash
curl "http://localhost:8080/stores?latitude=51.6167&longitude=5.5486"
```

### PostGIS E2E Testing

For the PostGIS feature branch, you can run the comprehensive E2E test suite:

```bash
./test-postgis-e2e.sh
```

This script will:
1. Build Docker images
2. Start PostGIS database
3. Start the application
4. Run E2E validation with the exact expected response
5. Clean up containers

## Azure CI Pipeline

The project includes an Azure DevOps pipeline specifically for the `features/postgis` branch:

### Pipeline Configuration

- **File**: `azure-pipelines-postgis.yml`
- **Trigger**: `features/postgis` branch
- **Stages**:
  1. **Build**: Compile and test the application
  2. **Integration Test**: Start PostGIS and application containers
  3. **E2E Validation**: Test the API with exact response validation
  4. **Deploy**: Deploy PostGIS version (if all tests pass)

### E2E Validation

The pipeline validates the exact API response for the Veghel coordinates:
- **Endpoint**: `GET /stores?latitude=51.6167&longitude=5.5486`
- **Expected Response**: 5 stores with specific distances
- **Validation**: Exact JSON match with distance calculations
### TODO list

* add reactive support using webflux
* integration with Azure DevOps (Pipeline, Boards)