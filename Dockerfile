FROM maven:3.8.3-jdk-11
WORKDIR /usr/local/service/orthodontic-clinic
COPY src /usr/local/service/orthodontic-clinic/src
COPY pom.xml /usr/local/service/orthodontic-clinic
RUN mvn -f /usr/local/service/orthodontic-clinic/pom.xml clean package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/OrthodonticClinic-0.0.1-SNAPSHOT.jar"]
