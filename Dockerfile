#Criando o ambiente do Maven para a execução do Spring Boot
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY src ./src
COPY pom.xml .

RUN mvn clean package -DskipTests

# O ambiente de execução irá utilizar a JDK da Azul Java 21
FROM azul/zulu-openjdk:21

WORKDIR /app

COPY --from=build /app/target/mobilidade_urbana-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]