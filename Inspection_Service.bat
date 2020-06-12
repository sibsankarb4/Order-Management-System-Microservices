echo off
mode 800
color 57
type banner.txt
timeout 10

::========================== Starting kowloon common-lib ===================================
start cmd.exe /k "Title kowloon common-lib & color 17 & cd C:\TFS_Workspace\Inspection-review\kowloon.common-lib & mvn clean package install"
timeout 25 


::========================== Starting Lease-end common-lib ===================================
start cmd.exe /k "Title lease-end common-lib & color 17 & cd C:\TFS_Workspace\Inspection-review\lease-end.common-lib & mvn clean package install"
timeout 25 


::========================== Starting multitenant lib ===================================
start cmd.exe /k "Title kowloon multitenant lib & color 17 & cd C:\TFS_Workspace\Inspection-review\kowloon.multitenant-lib & mvn clean package install -DskipTests"
timeout 30 


::========================== Starting tenant service ===================================
start cmd.exe /k "Title kowloon Tenant Service & color 37 & cd C:\TFS_Workspace\Inspection-review\lease-end.inspection-service & docker-compose up"
timeout 20 


::========================== population multitenant  ===================================
::cd C:\TFS_Workspace\Inspection-review\lease-end.inspection-service\.bin && start "" "C:\Program Files\Git\bin\sh.exe"
:: TENANT_SERVICE_ENDPOINT=http://localhost:18080 ./init-tenant.DEV.sh
::timeout 10
::start http://localhost:18080/swagger-ui.html
::timeout 10

::========================== Starting Inspection Service ===================================
start cmd.exe /k "Title Inspection Service & color 27 & cd C:\TFS_Workspace\Inspection-review\lease-end.inspection-service & mvn spring-boot:run -Dspring-boot.run.profiles=noticp"
timeout 30 
start http://localhost:8080/swagger-ui.html
::start http://localhost:18080/swagger-ui.html

::netstat -ano | findstr 8080
::for /f "tokens=5" %a in ('netstat -aon ^| find ":18080" ^| find "LISTENING"') do taskkill /f /pid %a

