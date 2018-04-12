FROM openjdk:8u162-jre-stretch

RUN mkdir /app
COPY build/libs/erdm-rest.jar /app
WORKDIR /app

EXPOSE 5000

CMD java -jar /app/erdm-rest.jar