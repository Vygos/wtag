FROM gradle AS build

WORKDIR /apt/app/
COPY . .
RUN ./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true

FROM alpine:latest
WORKDIR /opt/app
COPY --from=build ./build/wex-tag-1.0.0-SNAPSHOT-runner ./application
EXPOSE 8080
ENTRYPOINT ["./application", "Dquarkus.http.host=0.0.0.0"]
