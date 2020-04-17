FROM openjdk:8-jre
MAINTAINER David Flemström <dflemstr@spotify.com>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/nooty-authentication/nooty-authentication.jar"]

# Add Maven dependencies (not shaded into the artifact; Docker-cached)
ADD target/lib           /usr/share/nooty-authentication/lib
# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/nooty-authentication/nooty-authentication.jar