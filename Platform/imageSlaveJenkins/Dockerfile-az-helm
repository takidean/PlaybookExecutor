FROM jenkinsci/jnlp-slave:alpine

#maven:3.6.0-jdk-8-alpine
USER root

#RUN apk add maven --update-cache --repository http://dl-4.alpinelinux.org/alpine/edge/community/ #--allow-untrusted \
#	&& rm -rf /var/cache/apk/*

RUN apk add --update \
    python \
    python-dev \
    py-pip \
    build-base \
  && pip install virtualenv \
  && rm -rf /var/cache/apk/*

RUN pip install --upgrade pip
RUN \
  apk update && \
  apk add bash py-pip && \
  apk add --virtual=build gcc libffi-dev musl-dev openssl-dev python-dev make && \
  pip --no-cache-dir install -U pip && \
  pip --no-cache-dir install azure-cli && \
apk del --purge build

RUN pip install -I azure-nspkg
ENV BASE_URL="https://get.helm.sh"
ENV TAR_FILE="helm-v2.13.0-linux-amd64.tar.gz"

RUN apk add --update --no-cache curl ca-certificates && \
    curl -L ${BASE_URL}/${TAR_FILE} |tar xvz && \
    mv linux-amd64/helm /usr/bin/helm && \
    chmod +x /usr/bin/helm && \
    rm -rf linux-amd64 && \
    apk del curl && \
    rm -f /var/cache/apk/*


RUN apk add --no-cache git
#RUN apk add --no-cache bash
RUN apk add --update nodejs-lts
RUN apk add yarn
RUN apk update yarn
# Add user jenkins to the image
#RUN adduser -D jenkins
# Set password for the jenkins user (you may want to alter this).
#RUN echo "jenkins:jenkins" | chpasswd
RUN apk add npm
RUN apk update npm
RUN npm install -g n
RUN n lts
RUN npm install -g npm@latest
RUN npm -v
RUN mkdir /home/jenkins/.m2
RUN chown -R jenkins:jenkins /home/jenkins/.m2/

RUN mkdir /var/jenkins_home
RUN chown jenkins:jenkins /usr/lib/node_modules
RUN chown jenkins:jenkins /usr/lib/node_modules/npm 



COPY settings.xml /var/jenkins_home/
COPY maven_build.sh /var/jenkins_home/
COPY maven_build_pricing.sh /var/jenkins_home/
RUN touch /var/jenkins_home/.npmrc 
COPY n.npmrc /var/jenkins_home/.npmrc 
COPY ActivePivot.lic /var/jenkins_home/ActivePivot.lic
RUN chown jenkins:jenkins /var/jenkins_home/maven_build.sh
RUN chown jenkins:jenkins /var/jenkins_home/settings.xml
RUN chown jenkins:jenkins /var/jenkins_home/ActivePivot.lic
RUN chown jenkins:jenkins /var/jenkins_home/maven_build_pricing.sh 
RUN chmod +xw /var/jenkins_home/maven*
COPY jenkins-slave /usr/local/bin/jenkins-slave
COPY slave.jar /usr/share/jenkins/slave.jar
RUN chown jenkins:jenkins /root/
RUN chown jenkins:jenkins /home/jenkins/.m2
COPY settings.xml /home/jenkins/.m2/settings.xml
RUN chown jenkins:jenkins /home/jenkins/.m2/settings.xml
RUN chown jenkins:jenkins /var/jenkins_home/.npmrc


RUN MAVEN_VERSION=3.6.1 \
 && cd /usr/share \
 && wget -q http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz -O - | tar xzf - \
 && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
 && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV ACTIVEPIVOT_LICENSE /var/jenkins_home/ActivePivot.lic


USER jenkins

ENV ACTIVEPIVOT_LICENSE /var/jenkins_home/ActivePivot.lic

WORKDIR /home/jenkins


