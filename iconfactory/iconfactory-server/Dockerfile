FROM java:8

RUN mkdir -p /opt/argos/plugins
RUN mkdir -p /var/log/argos
RUN mkdir -p /etc/argos

ADD target/iconfactory-hibernate-1.0-SNAPSHOT-plugin.zip /opt/argos/plugins/iconfactory-hibernate-plugin.zip
ADD target/aniketos-security-framework-provider-1.0-SNAPSHOT-plugin.zip /opt/argos/plugins/aniketos-security-framework-provider-plugin.zip
ADD target/iconfactory-server.jar /opt/argos/iconfactory-server.jar

VOLUME ["/var/log/argos", "/etc/argos"]

EXPOSE 30102 40102

ENTRYPOINT ["java -jar /opt/argos/iconfactory-server.jar"]
