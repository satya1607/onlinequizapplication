# Stage 1: Build the app using Maven
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM openjdk:17
COPY --from=build /app/target/*.jar quizapplication-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "quizapplication-0.0.1-SNAPSHOT.jar"]
