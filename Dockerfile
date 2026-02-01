FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime image (unchanged)
FROM alpine/java:17-jdk
WORKDIR /app
RUN apk add --no-cache postgresql-client
COPY --from=builder /app/target/*.jar arquitetura_hexagonal.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "arquitetura_hexagonal.jar"]