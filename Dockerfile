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

#FROM amazoncorretto:8
#MAINTAINER francisco.fro.agui@gmail.com
#ARG JAR_FILE=target/api-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]
#EXPOSE 8282

FROM amazoncorretto:8
MAINTAINER francisco.fro.agui@gmail.com
ARG JAR_FILE=target/api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

COPY ./conf/servicoca.petrobras.com.br.crt .
COPY ./conf/vs-rj-int-kubernetes-intg.petrobras.com.br_.crt .

RUN $JAVA_HOME/bin/keytool -import -noprompt -trustcacerts -alias "ca-cert" -file ./servicoca.petrobras.com.br.crt -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit
RUN $JAVA_HOME/bin/keytool -import -noprompt -trustcacerts -alias "ca-kube" -file ./vs-rj-int-kubernetes-intg.petrobras.com.br_.crt -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit

#Comando para listar os certificados
#keytool -list -keystore "$JAVA_HOME/jre/lib/security/cacerts"
