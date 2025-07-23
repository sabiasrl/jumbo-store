FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /tests
COPY ./jumbo-stores-finder /tests
RUN mvn -f /tests/pom.xml dependency:go-offline
COPY e2e/run-tests.sh .
ENTRYPOINT ["./run-tests.sh"] 