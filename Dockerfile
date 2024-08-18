# Dockerfile
FROM tomcat:10.1.28-jdk17-temurin-jammy
COPY src/main/resources/db.properties /usr/local/tomcat/conf/db.properties
COPY target/taskRestJDBC.war /usr/local/tomcat/webapps/taskRestJDBC.war
