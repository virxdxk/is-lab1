FROM tomcat:10.1-jdk17-openjdk
WORKDIR /usr/local/tomcat/webapps
COPY app/build/libs/app.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]