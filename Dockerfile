FROM centos:latest
MAINTAINER  koseungbin  <kosb1563@gmail.com>


RUN yum install -y java-1.8.0-openjdk-devel.x86_64

ENV JAVA_HOME /usr/lib/jvm/java-1.8.0

RUN mkdir app

COPY . ${HOME}/app

RUN ls -al /app/
RUN ls -al /app/handicraft-web/

RUN  sh /app/run.sh

ENTRYPOINT sh /app/deploy.sh

WORKDIR /${HOME}/

EXPOSE 80
EXPOSE 443
EXPOSE 8080
