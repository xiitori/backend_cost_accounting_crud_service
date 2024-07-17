FROM openjdk:24

COPY ./target/crudService-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "/app/crudService-0.0.1-SNAPSHOT.jar"]