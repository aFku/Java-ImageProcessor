#!/bin/bash

# Make sure that Java and Maven are installed
java --version
mvn --version

# Values for testing purpose only
rabbit_password="test1234"
rabbit_user="user"
db_password="test1234"
db_user="user"
db_name="imageprocessor"
keycloak_admin="admin"
keycloak_password="admin"


cd AppComponents/ImageAdjusterApp
mvn package -Dmaven.test.skip=true

cd ../..

cd AppComponents/ProcessingService
mvn package -Dmaven.test.skip=true

cd ../..

sudo RABBITMQ_PASSWORD=$rabbit_password RABBITMQ_USER=$rabbit_user DB_PASSWORD=$db_password DB_USER=$db_user DB_NAME=$db_name KEYCLOAK_ADMIN=$keycloak_admin KEYCLOAK_ADMIN_PASSWORD=$keycloak_password docker compose up