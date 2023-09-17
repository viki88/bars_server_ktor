#FROM alpine
#EXPOSE 8080
#COPY quickstart.sh /
#CMD ["quickstart.sh"]

FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:17-jdk-alpine3.14
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/bars-server.jar
ENTRYPOINT ["java","-jar","/app/bars-server.jar"]