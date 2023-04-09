<<<<<<< HEAD
FROM alpine
=======
FROM openjdk:17-jdk-alpine
MAINTAINER "info@pedroseoaneprado.es"
EXPOSE 8080
COPY target/minesweeper-0.1.jar /minesweeper-0.1.jar
ENTRYPOINT ["java", "-jar", "/minesweeper-0.1.jar"]
>>>>>>> devel-gui

# Instalamos las dependencias necesarias para OpenJDK 17
RUN apk add --no-cache openjdk17

# Instalamos Maven (opcional si ya lo tienes instalado)
RUN apk add --no-cache maven

# Copiamos los archivos del proyecto a la imagen de Docker
COPY . /app

# Establecemos el directorio de trabajo
WORKDIR /app

# Compilamos el código fuente
RUN mvn clean package

# Exponemos el puerto 8080
EXPOSE 8080

# Ejecutamos la aplicación al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "target/minesweeper-0.1.jar"]
