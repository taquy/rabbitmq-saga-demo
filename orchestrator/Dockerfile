FROM openjdk:8-jdk-alpine
MAINTAINER taquy <taquy.se@gmail.com>
VOLUME /tmp
EXPOSE 8091
ARG JAR_FILE=target/service-broker-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} service-broker.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/service-broker.jar"]