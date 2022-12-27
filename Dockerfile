FROM openjdk:11
COPY target/banking-application-backend-0.0.1-SNAPSHOT.jar banking-application-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/banking-application-backend-0.0.1-SNAPSHOT.jar"]