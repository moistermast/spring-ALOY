# Troubleshooting Guide

This guide helps diagnose and fix common issues with the E-Commerce application.

## 🔍 **Diagnostic Steps**

### 1. **Check Application Health**
Visit: `http://localhost:8080/health`
- Should return JSON with status "UP" and database "CONNECTED"
- If not, there's a database connection issue

### 2. **Check Database State**
Visit: `http://localhost:8080/utils/debug-database`
- Shows categories and products count
- Lists all categories and products
- Helps identify if data is missing

### 3. **Check Application Logs**
Look for these log messages during startup:
```
Starting database initialization...
Creating initial data...
Categories count: 3
Products count: 9
Database initialization completed successfully!
```

## 🚨 **Common Issues & Solutions**

### **Issue 1: White Label Error Page**

**Symptoms:**
- Generic error page instead of proper content
- No specific error message

**Solutions:**
1. **Check database connection** - Visit `/health`
2. **Verify database exists** - Ensure database is created
3. **Check application logs** - Look for initialization errors
4. **Restart application** - Sometimes fixes temporary issues

### **Issue 2: No Images Displaying**

**Symptoms:**
- Product images not showing
- Broken image links
- Placeholder images only

**Solutions:**
1. **Verify image files exist** - Check `/src/main/resources/static/images/`
2. **Check image paths** - Should be `/images/filename.jpg`
3. **Clear browser cache** - Hard refresh (Ctrl+F5)
4. **Check file permissions** - Ensure images are readable

### **Issue 3: Catalogue Page Shows Error**

**Symptoms:**
- White label error on `/catalogue`
- No products displayed
- Categories not found

**Solutions:**
1. **Check database initialization** - Visit `/utils/debug-database`
2. **Verify categories exist** - Should have RINGS, CHAINS, BRACELETS
3. **Check product data** - Should have products in each category
4. **Restart with fresh database** - Delete database and restart

### **Issue 4: Shop Sections Not Working**

**Symptoms:**
- Product listing pages show errors
- Category filtering doesn't work
- Search functionality broken

**Solutions:**
1. **Check database connectivity** - Visit `/health`
2. **Verify product data** - Check `/utils/debug-database`
3. **Check controller mappings** - Ensure no conflicting routes
4. **Clear application cache** - Restart application

## 🛠️ **Quick Fixes**

### **Reset Database (Development)**
```bash
# Stop application
# Delete database files (if using H2/embedded)
# Restart application - it will recreate tables
mvn spring-boot:run
```

### **Force Data Initialization**
Visit: `http://localhost:8080/utils/create-initial-data`
This will create sample data if missing.

### **Check Database Tables**
If using PostgreSQL/MySQL, connect to database and run:
```sql
SELECT COUNT(*) FROM ec_category;
SELECT COUNT(*) FROM ec_product;
SELECT name FROM ec_category;
```

## 📋 **Debugging Checklist**

- [ ] Application starts without errors
- [ ] Database connection successful (`/health`)
- [ ] Categories exist (RINGS, CHAINS, BRACELETS)
- [ ] Products exist in each category
- [ ] Images are accessible (`/images/...`)
- [ ] No conflicting controller mappings
- [ ] Static resources are served correctly
- [ ] Thymeleaf templates are valid

## 🔧 **Advanced Debugging**

### **Enable Debug Logging**
Add to `application.properties`:
```properties
logging.level.com.sda.java3=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

### **Check Database Schema**
```sql
-- PostgreSQL
\dt ec_*

-- MySQL
SHOW TABLES LIKE 'ec_%';
```

### **Verify Image Paths**
Check browser developer tools:
1. Open browser dev tools (F12)
2. Go to Network tab
3. Refresh page
4. Look for failed image requests (404 errors)

## 📞 **Getting Help**

If issues persist:
1. Check application logs for specific error messages
2. Visit `/health` and `/utils/debug-database` endpoints
3. Verify database connection settings
4. Ensure all required files are present
5. Try restarting the application

## 🎯 **Expected Behavior**

After successful startup:
- Home page shows banner images
- Catalogue page displays products by category
- Product images load correctly
- Navigation works between pages
- No white label error pages 