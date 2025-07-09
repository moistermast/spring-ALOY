# Deployment Guide: Render + Neon PostgreSQL

This guide will help you deploy your Spring Boot e-commerce application to Render with Neon PostgreSQL.

## Prerequisites

1. **GitHub Account**: Your code should be in a GitHub repository
2. **Render Account**: Sign up at [render.com](https://render.com)
3. **Neon Account**: Sign up at [neon.tech](https://neon.tech)

## Step 1: Set Up Neon PostgreSQL Database

### 1.1 Create Neon Database
1. Go to [neon.tech](https://neon.tech) and sign up
2. Create a new project
3. Choose a region close to your users
4. Note down your connection details:
   - **Host**: `ep-xxx-xxx-xxx.region.aws.neon.tech`
   - **Database**: `neondb`
   - **Username**: `your-username`
   - **Password**: `your-password`

### 1.2 Get Connection String
Your Neon connection string will look like:
```
postgresql://username:password@ep-xxx-xxx-xxx.region.aws.neon.tech/neondb?sslmode=require
```

## Step 2: Deploy to Render

### 2.1 Connect GitHub Repository
1. Go to [render.com](https://render.com) and sign up
2. Click "New +" → "Web Service"
3. Connect your GitHub repository
4. Select the repository containing your Spring Boot application

### 2.2 Configure Environment Variables
In Render dashboard, add these environment variables:

```
DATABASE_URL=postgresql://username:password@ep-xxx-xxx-xxx.region.aws.neon.tech/neondb?sslmode=require
DB_USERNAME=your-username
DB_PASSWORD=your-password
HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
SPRING_PROFILES_ACTIVE=production
SERVER_PORT=8080
```

### 2.3 Build Configuration
- **Build Command**: `mvn clean package -DskipTests`
- **Start Command**: `java -jar target/ecommerce-app.jar`
- **Environment**: Java

### 2.4 Deploy
1. Click "Create Web Service"
2. Render will automatically build and deploy your application
3. Your app will be available at: `https://your-app-name.onrender.com`

## Step 3: Database Migration

### 3.1 Initial Setup
Your application will automatically create tables on first run because:
- `spring.jpa.hibernate.ddl-auto=update` is set
- The application will create all necessary tables

### 3.2 Verify Database
1. Go to your Neon dashboard
2. Check the "Tables" section to see your created tables:
   - `user`
   - `product`
   - `category`
   - `order`
   - `order_item`
   - `cart`

## Step 4: Test Your Application

### 4.1 Health Check
Visit: `https://your-app-name.onrender.com/actuator/health`

### 4.2 Test Features
1. **Home Page**: `https://your-app-name.onrender.com/`
2. **Product Catalog**: `https://your-app-name.onrender.com/catalogue`
3. **Cart**: Add products and test cart functionality

## Step 5: Custom Domain (Optional)

### 5.1 Add Custom Domain
1. In Render dashboard, go to your service
2. Click "Settings" → "Custom Domains"
3. Add your domain and configure DNS

### 5.2 SSL Certificate
Render automatically provides SSL certificates for custom domains.

## Troubleshooting

### Common Issues

1. **Build Failures**
   - Check Maven dependencies in `pom.xml`
   - Ensure Java 21 is specified
   - Verify all imports are correct

2. **Database Connection Issues**
   - Verify Neon connection string
   - Check firewall settings
   - Ensure SSL mode is set to `require`

3. **Application Startup Issues**
   - Check Render logs for errors
   - Verify environment variables
   - Ensure port 8080 is exposed

### Logs and Monitoring
- **Render Logs**: Available in Render dashboard
- **Application Logs**: Check Spring Boot logs in Render
- **Database Logs**: Available in Neon dashboard

## Cost Optimization

### Free Tier Limits
- **Render**: 750 hours/month free
- **Neon**: 3GB storage, 10GB transfer/month free

### Scaling Up
- **Render**: Upgrade to paid plan for more resources
- **Neon**: Upgrade for more storage and connections

## Security Best Practices

1. **Environment Variables**: Never commit secrets to Git
2. **Database**: Use Neon's built-in security features
3. **HTTPS**: Render provides automatic SSL
4. **CSRF Protection**: Already configured in the app

## Performance Tips

1. **Database**: Use Neon's connection pooling
2. **Caching**: Consider adding Redis for session storage
3. **CDN**: Use Cloudflare for static assets
4. **Monitoring**: Set up alerts in Render dashboard

## Support Resources

- [Render Documentation](https://render.com/docs)
- [Neon Documentation](https://neon.tech/docs)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot) 