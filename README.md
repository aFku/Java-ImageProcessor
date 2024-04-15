# Java-ImageProcessor

An application in form of microservices. It allows you to:
- Send image to processing with HTML form in Angular app hitting backend REST API
- Chose how you want to customize your image
- Process your image
- Fetch processed and original image from local storage
- Use Keycloak users as application users

## TO DO
- Processing service
- Tests

## Stack
- Java 17
- Spring Boot 3
- PostgreSQL
- RabbitMQ
- Keycloak
- Docker
- Websockets
- Angular

## How to deploy (Linux/WSL)

Deploying this applications requires working Docker service.
Please ensure that you have installed Java and Maven. Also check if you set up JAVA_HOME.
After that go to build-and-deploy.sh and change variables if needed, then:
```
./build-and-deploy.sh
```

This script will build all components and will run docker-compose.yaml which is responsible for deployment
