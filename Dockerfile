# Use Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set working folder inside container
WORKDIR /app

# Copy our built JAR into the container
COPY target/disasterplatform-0.0.1-SNAPSHOT.jar app.jar

# Open port 8080
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]