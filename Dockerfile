FROM openjdk:17
COPY *-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
