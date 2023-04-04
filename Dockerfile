FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive
ENV LD_LIBRARY_PATH=/usr/local/lib
ENV LIBRARY_PATH=/usr/local/lib

RUN apt-get -y -q update
RUN apt-get -y -q upgrade

# Other dependencies '
RUN apt-get -y install vim
RUN apt-get -y install maven
RUN apt-get -y install openjdk-17-jdk openjdk-17-jre
RUN apt-get -y install git tar wget nano curl
RUN apt-get update && apt-get install -y gnupg2

RUN wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | apt-key add -
RUN echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu jammy/mongodb-org/6.0 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-6.0.list
RUN apt-get update RUN apt-get install -y mongodb-org
RUN rm -rf /var/lib/apr/lists/*  \
    && rm -rf /var/lib/mongodb \
    && mv /etc/mongod.conf /etc/mognod.conf.orig
RUN mkdir -p /data/db /data/configdb
RUN chown -R mongodb:mongodb /data/db /data/configdb

RUN mkdir project
WORKDIR /root/project
COPY ./run.sh /root/project/

RUN chmod +x run.sh

CMD ["bash"]