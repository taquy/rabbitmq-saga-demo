FROM openjdk:8-jdk-alpine
MAINTAINER taquy <taquy.se@gmail.com>
VOLUME /tmp
EXPOSE 8093
ARG JAR_FILE=target/service.account-1.jar
ADD ${JAR_FILE} service.account-1.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/service.account-1.jar"]
