FROM openjdk:17
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
