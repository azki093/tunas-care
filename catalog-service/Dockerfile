FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY catalog-service/pom.xml catalog-service/pom.xml
COPY care-service/pom.xml care-service/pom.xml
COPY catalog-service/src catalog-service/src
RUN mvn -pl catalog-service -am package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/catalog-service/target/catalog-service-1.0.0.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
