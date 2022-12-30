FROM openjdk:11

COPY banking-application-backend-0.0.1-SNAPSHOT.jar banking.jar

CMD ["java", "-jar", "/banking.jar"]
