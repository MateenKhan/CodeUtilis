# Pull base image.
#FROM makjavaprogrammer/java8-tomcat8:2.0
FROM tomcat:8.5.4-jre8
MAINTAINER mateen <makjavaprogrammer@gmail.com>

WORKDIR /code

COPY /target/CodeUtilis.war /opt/tomcat/webapps/

EXPOSE 8080

VOLUME "/opt/tomcat/logs"

CMD ["/opt/tomcat/bin/catalina.sh", "run"]
