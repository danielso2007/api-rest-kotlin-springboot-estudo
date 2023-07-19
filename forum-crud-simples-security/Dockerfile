FROM openjdk:21-ea-17-slim-buster
EXPOSE 8080
ADD /target/forum-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]