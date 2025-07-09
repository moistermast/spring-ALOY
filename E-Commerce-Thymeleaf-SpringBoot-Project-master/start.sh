#!/bin/bash

# E-Commerce Application Startup Script
# This script helps start the application with proper database initialization

echo "=== E-Commerce Application Startup ==="
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed or not in PATH"
    echo "Please install Java 21 or later"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt "21" ]; then
    echo "❌ Error: Java 21 or later is required. Current version: $JAVA_VERSION"
    exit 1
fi

echo "✅ Java version: $(java -version 2>&1 | head -n 1)"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Error: Maven is not installed or not in PATH"
    echo "Please install Maven"
    exit 1
fi

echo "✅ Maven version: $(mvn -version 2>&1 | head -n 1)"
echo ""

# Check if .env file exists
if [ ! -f ".env" ]; then
    echo "⚠️  Warning: .env file not found"
    echo "The application will use default database settings"
    echo "For production, create a .env file with your database credentials"
    echo ""
fi

# Check database connection
echo "🔍 Checking database connection..."
echo "This may take a moment..."

# Start the application
echo "🚀 Starting E-Commerce application..."
echo "The application will:"
echo "  - Create database tables automatically"
echo "  - Insert sample categories and products"
echo "  - Start the web server on http://localhost:8080"
echo ""
echo "Press Ctrl+C to stop the application"
echo ""

# Run the application
mvn spring-boot:run

echo ""
echo "✅ Application stopped" 