FROM jboss/keycloak


RUN mkdir /opt/jboss/keycloak/modules/system/layers/keycloak/com/microsoft
RUN mkdir /opt/jboss/keycloak/modules/system/layers/keycloak/com/microsoft/main/
RUN cd /opt/jboss/keycloak/modules/system/layers/keycloak/com/microsoft/main/; curl -O  http://central.maven.org/maven2/com/microsoft/sqlserver/mssql-jdbc/7.2.2.jre8/mssql-jdbc-7.2.2.jre8.jar
ADD module.xml /opt/jboss/keycloak/modules/system/layers/keycloak/com/microsoft/main/

USER root

RUN chown jboss /opt/jboss/keycloak/standalone/configuration/standalone.xml
RUN chown jboss /opt/jboss/keycloak/modules/system/layers/keycloak/com/microsoft
RUN chown jboss /opt/jboss/keycloak/modules/system/layers/keycloak/com/microsoft/main/
RUN chown jboss /opt/jboss/keycloak/modules/system/layers/keycloak/com/microsoft/main/module.xml
