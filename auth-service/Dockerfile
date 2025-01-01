# Start with an openjdk base image
FROM openjdk:21-jdk-slim

# Copy the built JAR file from the target directory
COPY target/auth-service-0.0.1-SNAPSHOT.jar /app/auth-service.jar

# Set the entrypoint for the container
ENTRYPOINT ["java", "-jar", "/app/auth-service.jar"]