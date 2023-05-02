#!/usr/bin/env bash

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=clients-service \
--package-name=com.cardealership.clientservice \
--groupId=com.cardealership.clientservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
clients-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=employees-service \
--package-name=com.cardealership.employeeservice \
--groupId=com.cardealership.employeeservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
employees-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=inventory-service \
--package-name=com.cardealership.inventoryservice \
--groupId=com.cardealership.inventoryservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
inventory-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=purchases-service \
--package-name=com.cardealership.purchaseservice \
--groupId=com.cardealership.purchaseservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
purchases-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=api-gateway \
--package-name=com.cardealership.apigateway \
--groupId=com.cardealership.apigateway \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
api-gateway

