# Start with an openjdk base image
FROM openjdk:21-jdk-slim

# Copy the built JAR file from the target directory
COPY target/task-service-0.0.1-SNAPSHOT.jar /app/task-service.jar

# Set the entrypoint for the container
ENTRYPOINT ["java", "-jar", "/app/task-service.jar"]