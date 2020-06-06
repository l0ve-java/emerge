ARG JAR_FILE=target/odyssey-place-service.jar

FROM docker.soft-machine.ru/softmachine/springboot:latest

LABEL maintainer="devteam@soft-machine.ru"

ARG BUILD_VERSION
ENV VERSION=${BUILD_VERSION:-undefined}

COPY db-test-data db-provision
RUN chmod +x db-provision/*.sh

EXPOSE 8080
