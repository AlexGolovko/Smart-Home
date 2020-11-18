FROM openjdk:11
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-Xmx64M", "-jar", "/app.jar"]