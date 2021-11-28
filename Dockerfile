#STAGE 1:  BUILD
FROM maven:3.8-openjdk-17-slim as build
WORKDIR /
COPY . .
RUN mvn clean install -Dmaven.test.skip=true
RUN mv /target/geoposition-service-0.0.1-SNAPSHOT.jar /geoposition-service.jar

#STAGE 2:  RUN
FROM openjdk:18-slim
COPY --from=build /geoposition-service.jar /
ENTRYPOINT ["java", "-jar", "geoposition-service.jar"]