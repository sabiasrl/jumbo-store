#!/bin/bash
set -e

echo "🚀 Starting PostGIS E2E Test Suite"
echo "=================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Cleanup function
cleanup() {
    print_status "Cleaning up containers..."
    docker compose down -v
    docker system prune -f
}

# Set trap to cleanup on exit
trap cleanup EXIT

# Step 1: Build Docker images
print_status "Building Docker images..."
    docker compose build

# Step 2: Start PostGIS database
print_status "Starting PostGIS database..."
    docker compose up -d postgis

# Step 3: Wait for PostGIS to be ready
print_status "Waiting for PostGIS to be ready..."
for i in {1..30}; do
            if docker compose exec -T postgis pg_isready -U jumbo -d jumbo > /dev/null 2>&1; then
        print_status "PostGIS is ready!"
        break
    fi
    print_warning "Waiting for PostGIS... ($i/30)"
    sleep 10
done

# Final check for PostGIS
        if ! docker compose exec -T postgis pg_isready -U jumbo -d jumbo > /dev/null 2>&1; then
            print_error "PostGIS failed to start properly"
            docker compose logs postgis
    exit 1
fi

# Step 4: Start application
print_status "Starting application with PostGIS..."
    docker compose up -d jumbo-stores-finder

# Step 5: Wait for application to be ready
print_status "Waiting for application to start..."
for i in {1..60}; do
    if curl -s http://localhost:8080/actuator/health | grep -q "UP"; then
        print_status "Application is ready!"
        break
    fi
    print_warning "Waiting for application... ($i/60)"
    sleep 5
done

# Final check for application
if ! curl -s http://localhost:8080/actuator/health | grep -q "UP"; then
    print_error "Application failed to start properly"
                docker compose logs jumbo-stores-finder
    exit 1
fi

# Step 6: Run E2E validation
print_status "Running E2E validation test..."
print_status "Testing endpoint: http://localhost:8080/stores?latitude=51.6167&longitude=5.5486"

# Make the API call
RESPONSE=$(curl -s "http://localhost:8080/stores?latitude=51.6167&longitude=5.5486")
print_status "Response received:"
echo "$RESPONSE"

# Expected response (formatted for comparison)
EXPECTED='[{"addressName":"Jumbo Veghel de Boekt","latitude":51.609031,"longitude":5.546608,"distance":0.8637754032755139},{"addressName":"Jumbo Veghel De Bunders","latitude":51.623732,"longitude":5.558496,"distance":1.0383457053343215},{"addressName":"Jumbo Veghel Foodmarkt Veghel","latitude":51.615748,"longitude":5.52916,"distance":1.3463794546389556},{"addressName":"Jumbo Heeswijk Dinther St. Servatiusstraat","latitude":51.648961,"longitude":5.474725,"distance":6.23422969913618},{"addressName":"Jumbo Schijndel Rooiseheide","latitude":51.605829,"longitude":5.446227,"distance":7.1716009219970624}]'

# Remove whitespace and newlines for comparison
CLEAN_RESPONSE=$(echo "$RESPONSE" | tr -d ' \n\r')
CLEAN_EXPECTED=$(echo "$EXPECTED" | tr -d ' \n\r')

print_status "Verifying response matches expected output..."

if [ "$CLEAN_RESPONSE" = "$CLEAN_EXPECTED" ]; then
    print_status "✅ SUCCESS: Response matches expected output"
    print_status "✅ E2E validation passed successfully!"
    EXIT_CODE=0
else
    print_error "❌ FAILURE: Response does not match expected output"
    print_error "Expected: $CLEAN_EXPECTED"
    print_error "Received: $CLEAN_RESPONSE"
    print_error "❌ E2E validation failed!"
    EXIT_CODE=1
fi

print_status "Test completed with exit code: $EXIT_CODE"

if [ $EXIT_CODE -eq 0 ]; then
    print_status "🎉 All tests passed! PostGIS integration is working correctly."
else
    print_error "💥 Tests failed! Please check the logs above."
fi

exit $EXIT_CODE 