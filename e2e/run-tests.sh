#!/bin/bash
set -e
echo "Waiting for backend to be ready..."
until curl -s http://jumbo-stores-finder:8080/actuator/health | grep "UP"; do
  echo "Waiting for backend..."
  sleep 3
done

echo "Running E2E tests..."
mvn test --file /tests/pom.xml 