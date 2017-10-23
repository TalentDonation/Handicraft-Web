
# arguments : development or production for profiles

FROM centos:latest
MAINTAINER  koseungbin  <kosb1563@gmail.com>

ARG profiles
ARG projects

ENV PROFILES $profiles
ENV PROJECTS $projects

RUN yum install -y java-1.8.0-openjdk-devel.x86_64

ENV JAVA_HOME /usr/lib/jvm/java-1.8.0

RUN mkdir app

COPY . ${HOME}/app

# test:
RUN ls -al /app/
RUN ls -al /app/handicraft-web/

# arg : arguments for profiles
ENTRYPOINT sh /app/shellscript/deploy.sh ${PROFILES}  ${PROJECTS};


WORKDIR /${HOME}/

EXPOSE 80
EXPOSE 443
EXPOSE 8080
EXPOSE 9090
