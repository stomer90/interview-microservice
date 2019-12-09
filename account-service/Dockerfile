FROM openjdk:8-jre-alpine

LABEL author="PhongNV"

VOLUME /tmp
ADD ./target/*.jar /app.jar

RUN sh -c 'touch /app.jar'

ENV JAVA_OPTS="-Xmx256m"

EXPOSE 8892

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=docker -jar /app.jar" ]