FROM alpine:edge

MAINTAINER zoltan@mullner.hu

RUN echo "===> Installing sudo to emulate normal OS behavior..."  && \
    apk --update add sudo                                         && \
    \
    \
    echo "===> Adding Python runtime..."  && \
    apk --update add python py-pip openssl ca-certificates    && \
    apk --update add --virtual build-dependencies \
                python-dev libffi-dev openssl-dev build-base  && \
    pip install --upgrade pip cffi                            && \
    \
    \
    echo "===> Installing Ansible..."  && \
    pip install ansible==2.8.2         && \
    \
    \
    echo "===> Installing handy tools (not absolutely required)..."  && \
    pip install --upgrade pywinrm                  && \
    apk --update add sshpass openssh-client rsync  && \
    \
    \
    echo "===> Removing package list..."  && \
    apk del build-dependencies            && \
    rm -rf /var/cache/apk/*               && \
    \
    \
    echo "===> Adding hosts for convenience..."  && \
    mkdir -p /etc/ansible                        && \
    echo 'localhost' > /etc/ansible/hosts

RUN apk --no-cache --update add \
        bash \
        py-dnspython \
        py-boto \
        py-netaddr \
        bind-tools \
        html2text \
        php7 \
        php7-json \
        git \
        jq \
        curl

RUN pip install --no-cache-dir --upgrade yq
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

ADD https://storage.googleapis.com/kubernetes-release/release/v1.10.11/bin/linux/amd64/kubectl /usr/local/bin/kubectl

RUN set -x && \
    apk add --no-cache curl ca-certificates && \
    chmod +x /usr/local/bin/kubectl && \
    \
    # Basic check it works.
    kubectl version --client

RUN echo 'ipv6' >> /etc/modules
RUN echo 'net.ipv6.conf.all.disable_ipv6 = 1' >> /etc/sysctl.d/01-disable-ipv6.conf
RUN pip install --no-cache-dir --upgrade azure-cli
RUN pip install ansible[azure]
RUN mkdir /opt/builder/
RUN mkdir /root/.azure
RUN mkdir /opt/builder/logs/
COPY credentials /root/.azure
COPY template_resource_create.yaml /opt/builder/template_resource_create.yaml
COPY template_standard_cluster.yaml /opt/builder/template_standard_cluster.yaml
COPY template_cluster_tocreate.yaml /opt/builder/template_cluster_tocreate.yaml
COPY clusterCreator.war /opt/builder/clusterCreator.war
COPY external.properties /opt/builder/external.properties
COPY tls.key /opt/builder/tls.key
COPY tls.crt /opt/builder/tls.crt
COPY INGRESS.yaml /opt/builder/INGRESS.yaml
COPY keyckloak-service.yaml /opt/builder/keyckloak-service.yaml
COPY keycloak_pvc.yaml /opt/builder/keycloak_pvc.yaml
COPY storageclass.yaml /opt/builder/storageclass.yaml
COPY template_db_server.yaml /opt/builder/template_db_server.yaml
COPY template_db.yaml /opt/builder/template_db.yaml
COPY template_create_secret_db.yaml /opt/builder/template_create_secret_db.yaml
COPY template_create_secret_keycloak.yaml /opt/builder/template_create_secret_keycloak.yaml
COPY template_keycloak_deployment.yaml /opt/builder/template_keycloak_deployment.yaml
COPY template_keycloak.yaml /opt/builder/template_keycloak.yaml


RUN chmod +wx /opt/builder/clusterCreator.war
RUN apk update
RUN apk fetch openjdk8
RUN apk add openjdk8
RUN pip install --no-cache-dir --upgrade azure-cli
RUN ssh-keygen  -b 2048 -t rsa -f /root/.ssh/id_rsa -q -N ""
RUN mkdir -p /ansible/playbooks

#az login --service-principal --username "a084bea2-4c58-486f-a9a1-631af5454943" --password "4/lpor8MU*pnxjJGkyt+LWM-#VtC9w9Sr" --tenant "749303d0-d192-4c55-99f4-66679552732c"



VOLUME [ "/ansible/playbooks" ]
ENTRYPOINT ["java","-jar","/opt/builder/clusterCreator.war"]
# default command: display Ansible version
