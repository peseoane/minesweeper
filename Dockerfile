FROM openjdk:17-jdk-alpine
MAINTAINER "info@pedroseoaneprado.es"
COPY target/minesweeper-0.1.jar /minesweeper-0.1.jar
ENTRYPOINT ["java", "-jar", "/minesweeper-0.1.jar"]



