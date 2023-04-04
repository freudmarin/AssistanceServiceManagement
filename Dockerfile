FROM tomcat:8.5.47-jdk11-openjdk

COPY . /app
WORKDIR /app

COPY mvnw .
RUN apt-get update && apt-get install -y dos2unix && dos2unix mvnw && chmod +x mvnw && ./mvnw package -DskipTests

RUN cp /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]

ENV JAVA_HOME /usr/local/openjdk-11

