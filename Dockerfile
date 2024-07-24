FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/*.jar homework.jar
EXPOSE 8080
LABEL maintainer="Vladislav Sytyi"
ENTRYPOINT ["java","-jar","/homework.jar"]
