A simple Spring Boot application registers all postal dispatches, their progression over post offices and receipt by the customer. Also there is a functionality of getting all the information of the delivery and its history of progression. The app is a JSON service (war-archived) using PostreSQL (Docker) with Spring Data and Liquibase, built with Maven.

Operations:

- registration of a delivery item,
- its arrival to the post office,
- its departure from the post office,
- its receipt by the addressee,
- its status and overall history review.

Endpoints:

/register \
Post request, JSON to have attributes: "receiverName", "receiverIndex", "receiverAddress", "deliveryType". DeliveryType is enum that has 5 possible values: UNDEFINED, LETTER, PACKAGE, PARCEL, POSTCARD. If the value is not presented in JSON default set value is UNDEFINED.

/arrive/{deliveryId}/{postOfficeId} \
Post request, ids are sent in the path. Response: ResponseEntity<DeliveryDTO> with a registered delivery id.

/depart/{deliveryId}/{postOfficeId} \
Post request, ids are sent in the path.  Response: ResponseEntity<Boolean>.

/receive/{deliveryId} \
Post request, id is sent in the path. Response: ResponseEntity<Boolean>.

/status/{deliveryId} \
Get request, id is sent in the path. Response: Response: ResponseEntity<DeliveryDTO> with all the history as Gate objects that have arrival date and departure date. Also the DeliveryDTO has boolean field of receipt status.

The app is dockerized and to run it you need to have Docker installed. Follow these steps:
1. pull the project
2. run Maven command in the terminal: mvn clean package
3. run in the terminal in the directory of the project: docker build -t postal .
4. run in the terminal in the directory of the project: docker-compose up
5. now the app should be running at your localhost:80 port
6. to shut down the app run as well in the terminal in the project dir: docker-compose down