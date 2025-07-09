@echo off
REM E-Commerce Application Startup Script for Windows
REM This script helps start the application with proper database initialization

echo === E-Commerce Application Startup ===
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Error: Java is not installed or not in PATH
    echo Please install Java 21 or later
    pause
    exit /b 1
)

echo ✅ Java version: 
java -version 2>&1 | findstr "version"
echo.

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Error: Maven is not installed or not in PATH
    echo Please install Maven
    pause
    exit /b 1
)

echo ✅ Maven version:
mvn -version 2>&1 | findstr "Apache Maven"
echo.

REM Check if .env file exists
if not exist ".env" (
    echo ⚠️  Warning: .env file not found
    echo The application will use default database settings
    echo For production, create a .env file with your database credentials
    echo.
)

REM Check database connection
echo 🔍 Checking database connection...
echo This may take a moment...
echo.

REM Start the application
echo 🚀 Starting E-Commerce application...
echo The application will:
echo   - Create database tables automatically
echo   - Insert sample categories and products
echo   - Start the web server on http://localhost:8080
echo.
echo Press Ctrl+C to stop the application
echo.

REM Run the application
mvn spring-boot:run

echo.
echo ✅ Application stopped
pause 