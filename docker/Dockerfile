FROM jenkins/jenkins:lts-jdk17

USER root

## Basic Util Tools ##
RUN apt-get update && apt-get install -y --no-install-recommends \
git curl unzip ca-certificates && rm -rf /var/lib/apt/lists/*

## AWS CLI v2 Install ##
RUN curl -fsSL "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o /tmp/awscliv2.zip \
 && unzip -q /tmp/awscliv2.zip -d /tmp \
 && /tmp/aws/install \
 && rm -rf /tmp/aws /tmp/awscliv2.zip \
 && aws --version

## Plugin Install ##
COPY docker/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt

USER jenkins
