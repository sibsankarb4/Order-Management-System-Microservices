start cmd.exe /c Kafka_Delete_Logs.bat

color 57
type banner.txt
timeout 10


::========================== Starting Zipkin ===================================
start cmd.exe /k "Title Zipkin Server & color 47 & cd C:\softwares & java -jar zipkin-server-2.21.0.jar"
timeout 10
start http://localhost:9411/
timeout 10


::========================== Starting Zookeeper/Kafka ===================================
::starting Zookeeper server
start cmd.exe /k "Title Zookeeper Server & color 37 & cd C:\kafka_2.12-2.3.1 & zookeeper-server-start config\zookeeper.properties"
timeout 10

::starting Kafka Node-1
start cmd.exe /k "Title Kafka Node-1 & color 17 & cd C:\kafka_2.12-2.3.1 & kafka-server-start config\server.properties"
timeout 10

::starting Kafka Node-2
start cmd.exe /k "Title Kafka Node-2 & color 17 & cd C:\kafka_2.12-2.3.1 & kafka-server-start config\server2.properties"
timeout 10

::starting Kafka Node-3
start cmd.exe /k "Title Kafka Node-3 & color 17 & cd C:\kafka_2.12-2.3.1 & kafka-server-start config\server3.properties"
timeout 10


::========================== Starting Applications ===================================
::starting Spring Boot Admin
start cmd.exe /k "Title Spring Boot Admin & color 27 & cd C:\redhat_workspace\Order_Management_wksp\Spring-Boot-Admin-Server & mvn spring-boot:run"
timeout 10
start http://localhost:8091/
timeout 10

::starting Eureka Server
start cmd.exe /k "Title Eureka Server & color 27 & cd C:\redhat_workspace\Order_Management_wksp\Eureka-Server & mvn spring-boot:run"
timeout 10
start http://localhost:8092/
timeout 10

::starting Orchestrator:Zuul Gateway
start cmd.exe /k "Title Orchestrator-Zull Gateway & color 27 & cd C:\redhat_workspace\Order_Management_wksp\Orchestrator & mvn spring-boot:run"
timeout 10
start http://localhost:8093/swagger-ui.html
timeout 10


::starting Product Microservice
start cmd.exe /k "Title Product Microservice & color 87 & cd C:\redhat_workspace\Order_Management_wksp\Product-Microservice & mvn spring-boot:run"
timeout 10

::starting Customer Microservice
start cmd.exe /k "Title Customer Microservice & color 87 & cd C:\redhat_workspace\Order_Management_wksp\Customer-Microservice & mvn spring-boot:run"
timeout 10

::starting Order Microservice
start cmd.exe /k "Title Order Microservice & color 87 & cd C:\redhat_workspace\Order_Management_wksp\Order-Microservice & mvn spring-boot:run"
timeout 10

::starting Event Consumer Microservice
start cmd.exe /k "Title Event Consumer Microservice & color 87 & cd C:\redhat_workspace\Order_Management_wksp\Event-Consumer-Service & mvn spring-boot:run"
timeout 10

