# arguments : development or production for profiles

FROM centos:latest
MAINTAINER  koseungbin  <kosb1563@gmail.com>

ARG profiles
ARG projects

ENV JAVA_HOME /usr/lib/jvm/java-1.8.0
ENV PROFILES $profiles
ENV PROJECTS $projects

WORKDIR /${HOME}/

EXPOSE 80
EXPOSE 443
EXPOSE 8080
EXPOSE 9090

RUN yum install -y java-1.8.0-openjdk-devel
RUN mkdir app

COPY . ${HOME}/app

WORKDIR /app/handicraft-web
RUN sh mvnw -pl ./handicraft-core -Dspring.profiles.active="${PROFILES}-core" clean install
ENTRYPOINT sh mvnw -pl ./handicraft-${PROJECTS} -Dspring.profiles.active="${PROFILES}-${PROJECTS}" clean spring-boot:run
