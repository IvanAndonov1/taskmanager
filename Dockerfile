# Use lightweight Java runtime
FROM openjdk:17-jdk-slim

# Set working dir
WORKDIR /app

# Copy jar from target folder
COPY target/taskmanager-0.0.1-SNAPSHOT.jar app.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
