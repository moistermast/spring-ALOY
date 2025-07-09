# Neon Database Setup for E-Commerce-Thymeleaf-SpringBoot-Project

This guide will help you set up your Neon PostgreSQL database and connect it to your Spring Boot application.

## 1. Create a Neon Project

If you don't have a Neon account, sign up at [Neon](https://neon.tech/).
Once logged in, create a new project. Neon will provide you with connection details for your new database, including:
- **Endpoint**: This is your `YOUR_NEON_HOST` (e.g., `ep-patient-flower-123456.us-east-2.aws.neon.tech`).
- **Database Name**: This is typically `neondb` or a name you specified (e.g., `YOUR_DATABASE_NAME`).
- **User**: This is your `YOUR_DATABASE_USERNAME` (e.g., `your_username`).
- **Password**: This is your `YOUR_DATABASE_PASSWORD`.

## 2. Configure Your `.env` File

As instructed previously, you need to create a `.env` file in the root of your project (i.e., `E-Commerce-Thymeleaf-SpringBoot-Project-master/`).

Ensure the `.env` file contains the following, with your actual Neon credentials:

```properties
DATABASE_URL=jdbc:postgresql://YOUR_NEON_HOST/YOUR_DATABASE_NAME
DB_USERNAME=YOUR_DATABASE_USERNAME
DB_PASSWORD=YOUR_DATABASE_PASSWORD
HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
```

**Important:** Replace the placeholder values with the credentials you obtained from your Neon project.

## 3. Verify Database Connection

Once you have configured the `.env` file, you can try running your Spring Boot application. The application should now be able to connect to your Neon PostgreSQL database.

If you encounter any issues, double-check your `.env` file for typos and ensure your Neon database is accessible.

## Step 4: Run Your Application

```bash
mvn spring-boot:run
```

The application will automatically:
1. Connect to your Neon database
2. Create tables if they don't exist
3. Start the web server

## Step 5: Verify Integration

1. **Check application logs** for successful database connection
2. **Visit http://localhost:8080** to test the application
3. **Check Neon dashboard** to see created tables

## Troubleshooting

### Connection Issues
- Verify your connection string format
- Check if SSL mode is set to `require`
- Ensure your Neon database is active

### Table Creation Issues
- Run the migration scripts in Neon SQL Editor
- Check application logs for Hibernate errors

### Performance Issues
- Neon provides connection pooling automatically
- Consider using Neon's connection pooler for better performance 