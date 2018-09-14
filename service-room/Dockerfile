FROM openjdk:8-jdk-alpine
MAINTAINER taquy <taquy.se@gmail.com>
VOLUME /tmp
EXPOSE 8092
ARG JAR_FILE=target/service.room-1.jar
ADD ${JAR_FILE} service.room-1.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/service.room-1.jar"]
