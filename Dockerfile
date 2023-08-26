FROM openjdk
WORKDIR chat
ADD target/postal-0.0.1.war postal-0.0.1.war
ENTRYPOINT java -jar postal-0.0.1.war