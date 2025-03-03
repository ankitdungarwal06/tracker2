#Use official OpenJDK
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

#copy the application JAR file into the container
COPY target/tracker2.jar tracker.jar

#Expose the port your application runs on
EXPOSE 8080

#entrypoint
ENTRYPOINT ["java", "-jar", "tracker.jar"]