FROM alpine:3.9

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
    pip install ansible==2.7.9         && \
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

RUN \
  apk update && \
  apk add bash py-pip && \
  apk add --virtual=build gcc libffi-dev musl-dev openssl-dev python-dev make && \
  pip --no-cache-dir install -U pip && \
  pip --no-cache-dir install azure-cli && \
apk del --purge build

RUN pip install --no-cache-dir --upgrade azure-cli

RUN apk update
RUN apk fetch openjdk8
RUN apk add openjdk8

RUN mkdir -p /ansible/playbooks

WORKDIR /ansible/playbooks

VOLUME [ "/ansible/playbooks" ]
# default command: display Ansible version
