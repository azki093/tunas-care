FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY catalog-service/pom.xml catalog-service/pom.xml
COPY care-service/pom.xml care-service/pom.xml
COPY care-service/src care-service/src
RUN mvn -pl care-service -am package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/care-service/target/care-service-1.0.0.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
