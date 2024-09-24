FROM amazoncorretto:21
WORKDIR /app
COPY target/recommendations-engine-0.0.1-SNAPSHOT.jar /app/recommendations-engine.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "recommendations-engine.jar", "-Djava.rmi.server.hostname=localhost"]
