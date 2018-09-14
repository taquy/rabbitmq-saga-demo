FROM openjdk:8-jdk-alpine
MAINTAINER taquy <taquy.se@gmail.com>
VOLUME /tmp
EXPOSE 8091
ARG JAR_FILE=target/sage.service.main-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} sage.service.main-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sage.service.main-0.0.1-SNAPSHOT.jar"]
