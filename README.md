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
### TODO list

* add reactive support using webflux
* integration with Azure DevOps (Pipeline, Boards)