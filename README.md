# Banking Spring Boot Backend Application

This is a Spring Boot application that uses Swagger, PostgreSQL, EC2, Twilio, and RDS.

## Prerequisites

Before you begin, you will need the following:

- A PostgreSQL database running on EC2 or RDS
- A Twilio account and API key
- Java 11 or later
- Maven 3.6 or later

## Setting up the database

To set up the PostgreSQL database:

1. Launch an EC2 instance or create an RDS database.
2. Install and configure PostgreSQL on the instance or database.
3. Create a database and user for the application.
4. Update the `application.yml` file with the connection details for the database.

## Building and running the application

To build and run the application:

1. Clone this repository.
2. Run `mvn clean install` to build the application.
3. Run `java -war target/banking-application-backend.war` to start the application.

## Using the API

To use the API, you can use Swagger UI, which is available at `http://localhost:8090/swagger-ui.html` when the application is running. You can also use a tool like Postman to make API requests.

To use the Twilio API, you will need to set environment variables: TwillioSID, TwillioAuthToken, TwillioPhoneNumber.
These variables are used within the `LoginLogoutService.java`.

## Configuration

You can configure the application by modifying the `application.yml` file.
