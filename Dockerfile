FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive
ENV LD_LIBRARY_PATH=/usr/local/lib
ENV LIBRARY_PATH=/usr/local/lib

RUN apt-get -y -q update
RUN apt-get -y -q upgrade

# Other dependencies
RUN apt-get -y install vim
RUN apt-get -y install maven
RUN apt-get -y install openjdk-17-jdk openjdk-17-jre
RUN apt-get -y install git tar

RUN mkdir project
WORKDIR /root/project

COPY ./run.sh /root/project/
RUN chmod +x run.sh

CMD ["bash"]