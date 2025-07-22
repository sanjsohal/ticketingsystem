FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar ticketingsystem-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-Xmx512m", "-Xms128m", "-jar", "app.jar"]
