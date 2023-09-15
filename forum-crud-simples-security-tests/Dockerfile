FROM openjdk:21-ea-17-slim-buster
EXPOSE 8080
ADD /target/forum-*.jar app.jar
ENTRYPOINT ["java", "$JAVA_OPTS -XX:+UseContainerSupport", "-Xmx300m -Xss512k -XX:CICompilerCount=2", "-Dserver.port=$PORT", "-Dspring.profiles.active=prod", "-jar", "app.jar"]