FROM maven:3.8.3-openjdk-17 as builder
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17.0.2-jdk as runner
COPY --from=builder /home/app/target/placesearch-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/demo.jar"]

