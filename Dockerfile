#
# Build stage
#
FROM adoptopenjdk:8-jdk-openj9-bionic AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN wget http://www.us.apache.org/dist/tomcat/tomcat-6/v6.0.44/bin/apache-tomcat-6.0.44.tar.gz
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM adoptopenjdk:8-jdk-openj9-bionic
COPY --from=build /home/app/target/authentication-service-1.0-SNAPSHOT.jar /usr/local/lib/authentication-service-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/authentication-service-1.0-SNAPSHOT.jar"]