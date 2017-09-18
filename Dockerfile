
# arguments : development or production for profiles

ARG arg

FROM centos:latest
MAINTAINER  koseungbin  <kosb1563@gmail.com>

ARG profile 
ENV PROFILE $profile
RUN yum install -y java-1.8.0-openjdk-devel.x86_64

ENV JAVA_HOME /usr/lib/jvm/java-1.8.0

RUN mkdir app

COPY . ${HOME}/app

RUN ls -al /app/
RUN ls -al /app/handicraft-web/

# arg : arguments for profile
RUN  sh /app/shellscript/run.sh ${PROFILE}
ENTRYPOINT sh /app/shellscript/deploy.sh ${PROFILE}

WORKDIR /${HOME}/

EXPOSE 80
EXPOSE 443
EXPOSE 8080
EXPOSE 9090
