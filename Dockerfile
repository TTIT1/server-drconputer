# Use OpenJDK 17 slim image
FROM eclipse-temurin:23-jdk as builder

WORKDIR /app

# Copy the existing JAR file
COPY target/HustBooking-0.0.1-SNAPSHOT.jar app.jar

# Add wait-for-it script to check database connection
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

# Expose port
EXPOSE ${PORT:-8080}

# Wait for MySQL and start application
CMD ["./wait-for-it.sh", "mysql.railway.internal:3306", "--", "java", "-jar", "app.jar"]
