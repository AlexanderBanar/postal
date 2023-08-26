FROM openjdk
WORKDIR postal
ADD target/postal-0.0.1.war postal.war
ENTRYPOINT java -jar postal.war
#build the war archive of the app: mvn clean package
#build the image: docker build -t postal .