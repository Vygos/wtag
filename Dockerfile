FROM gradle AS build

WORKDIR /opt/app/
COPY . .
RUN ./gradlew build -Dquarkus.package.type=uber-jar -x test

FROM openjdk:17
WORKDIR /opt/app
COPY --from=build /opt/app/build/wex-tag-1.0.0-SNAPSHOT-runner.jar ./application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar", "-Dquarkus.http.host=0.0.0.0"]
