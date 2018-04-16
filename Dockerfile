FROM java:8u111-jdk
VOLUME /tmp
RUN mkdir /app
COPY build/libs/erdm-rest.jar /app
WORKDIR /app
EXPOSE 5000
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/erdm-rest.jar"]