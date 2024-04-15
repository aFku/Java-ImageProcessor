FROM openjdk:17-jdk-alpine
RUN apk --no-cache add curl
COPY target/ImageAdjusterApp-0.0.1-SNAPSHOT.jar ImageAdjusterApp-0.0.1-SNAPSHOT.jar
RUN mkdir /mnt/raw
RUN mkdir /mnt/proc
ENTRYPOINT ["java","-jar","/ImageAdjusterApp-0.0.1-SNAPSHOT.jar"]