FROM openjdk:17
COPY target/webmarketTestTask-*.jar /usr/app/webmarketTestTask.jar
WORKDIR /usr/app/
EXPOSE 8080:8080
ENTRYPOINT ["java","-jar","webmarketTestTask.jar"]