FROM gradle:8.11.1-jdk21-graal AS builder
WORKDIR /usr/app/
COPY build.gradle.kts settings.gradle.kts gradle.properties /usr/app/
RUN mkdir src
COPY src /usr/app/src/

# temporary disable native build
# RUN gradle clean quarkusBuild -Dquarkus.package.jar.enabled=false -Dquarkus.native.enabled=true --no-daemon 
RUN gradle clean build --no-daemon 

FROM ubuntu:jammy
WORKDIR /work/
RUN chown 1001 /work \
    && chmod "g+rwX" /work \
    && chown 1001:root /work
# COPY --from=builder --chown=1001:root /usr/app/build/*-runner /work/application
COPY --from=builder --chown=1001:root /usr/app/build/quarkus-app/quarkus-run.jar /work/

EXPOSE 8080
USER 1001

ENTRYPOINT ["./application", "-Dquarkus.http.host=0.0.0.0"]
