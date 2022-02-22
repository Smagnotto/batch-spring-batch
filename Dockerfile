FROM maven:3.8.3-openjdk-11 as build

WORKDIR /home/migration-persons
COPY . .

RUN mvn clean package

FROM openjdk:11.0.13-slim

COPY --from=build /home/migration-persons/target/*.jar ./app.jar

ENTRYPOINT ["java","-jar","/app.jar"]