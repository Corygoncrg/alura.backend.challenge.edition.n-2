FROM eclipse-temurin:17-jdk-focal

RUN apt-get update && apt-get install -y netcat
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]