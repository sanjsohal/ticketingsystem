FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/*.jar ticketingbackend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/ticketingbackend.jar"]

