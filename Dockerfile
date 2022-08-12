#FROM amazoncorretto:17
#MAINTAINER francisco.fro.agui@gmail.com
#ARG JAR_FILE=target/api-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
#ENV PROFILE_SPRING=test
#ENV PORT=8181
#ENTRYPOINT ["java", "-jar", "/app.jar"]
#EXPOSE ${PORT}

#FROM amazoncorretto:17
#MAINTAINER francisco.fro.agui@gmail.com
#ARG JAR_FILE=target/api-0.0.1-SNAPSHOT.jar
#ARG EXPOSE_PORT=8080
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]
#EXPOSE ${PORT}

FROM amazoncorretto:17
MAINTAINER francisco.fro.agui@gmail.com
ARG JAR_FILE=target/api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8282