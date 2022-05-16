FROM openjdk:17-alpine
MAINTAINER Phi
EXPOSE 8069
COPY target/quiz-1.jar quiz.jar
ENTRYPOINT ["java","-jar","/quiz.jar"]