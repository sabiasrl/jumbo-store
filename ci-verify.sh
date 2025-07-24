#!/bin/bash
set -e

# Remove existing reports directory to ensure a clean run
rm -rf reports

# Create reports directory to ensure volume mount works
mkdir -p reports/site

# Run tests and shut down containers afterwards, returning the exit code from the e2e container.
docker-compose up --build --abort-on-container-exit --exit-code-from e2e
EXIT_CODE=$?

# Check if tests passed
if [ $EXIT_CODE -eq 0 ]; then
  echo "E2E tests passed. Generating and printing site reports..."
  
  # Run a temporary container to generate the site and print the report
  docker-compose run --rm e2e mvn -f /tests/pom.xml --quiet site
  echo "HTML report generated at reports/site/surefire-report.html"
  echo "Script finished successfully."
  exit 0
else
  docker-compose down
  echo "E2E tests failed."
  exit 1
fi 