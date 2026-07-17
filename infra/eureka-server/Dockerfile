FROM maven:3.9-eclipse-temurin-21-alpine as builder
WORKDIR /app
COPY pom.xml .
COPY infra/ infra/
COPY services/ services/
COPY shared/ shared/
RUN mvn clean package -pl infra/eureka-server -am -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/infra/eureka-server/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
