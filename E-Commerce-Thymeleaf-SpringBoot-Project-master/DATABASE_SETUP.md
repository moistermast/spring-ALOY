# Database Setup Guide

This guide explains how to set up the database for the E-Commerce application and ensure all required tables are created.

## Database Schema

The application uses the following tables (all prefixed with `ec_`):

1. **ec_category** - Product categories
2. **ec_product** - Products
3. **ec_user** - User accounts
4. **ec_cart** - Shopping cart items
5. **ec_order** - Orders
6. **ec_order_item** - Order line items

## Automatic Database Initialization

The application is configured to automatically create tables and populate initial data when it starts. This is handled by:

1. **Spring Boot Auto-Configuration**: The application uses `spring.jpa.hibernate.ddl-auto=create-drop` to automatically create tables from JPA entities
2. **SQL Initialization**: The `schema.sql` and `data.sql` files in `src/main/resources/` are automatically executed
3. **Java Initialization**: The `DatabaseInitializer` class provides additional initialization logic

## Setup Options

### Option 1: Automatic Setup (Recommended)

The application will automatically create all tables and populate initial data when it starts. Simply run:

```bash
mvn spring-boot:run
```

The application will:
- Create all database tables
- Insert sample categories (RINGS, CHAINS, BRACELETS)
- Insert sample products for each category
- Log the initialization process

### Option 2: Manual Database Setup

If you prefer to set up the database manually:

1. **Create the database** (if using PostgreSQL):
   ```sql
   CREATE DATABASE aloy;
   ```

2. **Run the setup script**:
   ```bash
   psql -h your_host -U your_username -d aloy -f setup-database.sql
   ```

3. **Start the application**:
   ```bash
   mvn spring-boot:run
   ```

### Option 3: Using Spring Boot SQL Initialization

The application includes `schema.sql` and `data.sql` files that will be automatically executed. To use this:

1. Ensure your `application.properties` has:
   ```properties
   spring.jpa.hibernate.ddl-auto=create-drop
   spring.sql.init.mode=always
   spring.jpa.defer-datasource-initialization=true
   ```

2. Start the application - it will automatically execute the SQL files

## Configuration Files

### Application Properties

The following properties control database initialization:

- `spring.jpa.hibernate.ddl-auto=create-drop` - Creates tables from JPA entities
- `spring.sql.init.mode=always` - Always execute SQL initialization files
- `spring.jpa.defer-datasource-initialization=true` - Defer SQL initialization until after JPA initialization

### SQL Files

- `src/main/resources/schema.sql` - Table creation SQL
- `src/main/resources/data.sql` - Initial data insertion SQL
- `setup-database.sql` - Manual setup script

## Verification

To verify that tables are created correctly:

1. **Check application logs** for initialization messages
2. **Query the database**:
   ```sql
   -- Check tables exist
   \dt ec_*
   
   -- Check data
   SELECT COUNT(*) FROM ec_category;
   SELECT COUNT(*) FROM ec_product;
   ```

3. **Access the application** at `http://localhost:8080` and verify products are displayed

## Troubleshooting

### Tables Not Created

1. Check database connection in `application.properties`
2. Verify database credentials
3. Check application logs for errors
4. Ensure database exists and is accessible

### Data Not Populated

1. Check if `DatabaseInitializer` is running (look for log messages)
2. Verify `data.sql` is being executed
3. Check for SQL errors in application logs

### Production Deployment

For production, consider:

1. **Change `ddl-auto` to `validate`** after initial setup:
   ```properties
   spring.jpa.hibernate.ddl-auto=validate
   ```

2. **Use database migrations** like Flyway or Liquibase for schema changes

3. **Backup your data** before running initialization scripts

## Database Connection

### Development (MySQL)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/aloy
spring.datasource.username=root
spring.datasource.password=Allahisgr8yo
```

### Production (PostgreSQL/Neon)
```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

## Initial Data

The application creates the following initial data:

### Categories
- RINGS - Elegant rings for every occasion
- CHAINS - Stylish chains and necklaces  
- BRACELETS - Beautiful bracelets and wrist accessories

### Sample Products
- 3 sample rings
- 3 sample chains
- 3 sample bracelets

Each product includes:
- Name, description, price
- Image path
- Sale information (if applicable)
- Featured status
- Color and size options
- View count

## Security Notes

- Database credentials should be stored as environment variables in production
- Never commit database passwords to version control
- Use connection pooling for better performance
- Consider using SSL connections for production databases 