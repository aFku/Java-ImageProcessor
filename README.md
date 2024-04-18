# Java-ImageProcessor

An application in form of microservices. It allows you to:
- Send image to processing with HTML form in Angular app hitting backend REST API
- Chose how you want to customize your image
- Process your image
- Fetch processed and original image from local storage
- Use Keycloak users as application users

## Disclaimer

Application is fully functional. However, front-end is not high quality, because there was never intention to create beautiful GUI for this application. Main goal of this project was to improve my back-end skills. In order to show e.g working websockets, I had to create some UI, but that's all. Also this simple Angular project taught me something about CSRF and CORS, so creating it was necessary, but spending long hours to make it good looking not at all.

To sum up: GUI is ugly, but it allows you to use all planned features from application (Send image, Receive notifications form WS, Log in / Log out, List all raw and processed images that you send with processing attributes, experience all kinds of error responses)

## TO DO

### Application is fully functional and ready to deploy, but there are still some things to do:

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
