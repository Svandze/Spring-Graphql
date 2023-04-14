FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8085
ARG JAR_FILE=target/graphql-springboot-mongodb-example-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#Crear iamgen docker del proyecto
#Crear archivo "Dockerfile" con la siguiente informacion
#FROM openjdk:17-jdk-slim
 #VOLUME /tmp
 #EXPOSE 8085
 #ARG JAR_FILE=target/graphql-springboot-mongodb-example-1.0-SNAPSHOT.jar " el target e la direccion donde se crear el jar
 #necesario para crear el docker file "
 #ADD ${JAR_FILE} app.jar
 #ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
# Ejecutar los siguiente comando en la terminal "mvn package"
# "docker build -t <ASignar nomer al contenedor> ."
# docker run -p 8086:8087 <nombre del contenedor> y los puertos son configurables