FROM openjdk
WORKDIR postal
ADD target/postal-0.0.1.jar postal.jar
ENTRYPOINT java -jar postal.jar
#build the jar of the app: mvn clean package
#build the image: docker build -t postal .