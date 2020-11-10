# Game of Three

### Prerequisites
## Mac and Ubuntu/any debian based linux 
1. Install OpenJDK 8 or JDK 8 or above from Oracle. Tested on Java 8
2. Build automation tool Maven version 3.5 or above. Tested on 3.6.3.
3. Git.
4. IDE Intellij or eclipse. This app was developed using Intellij.
5. IDE is not mandatory to build and run the application. The application can be built using CLI. 

## Windows
1. On Windows maven has some compatibility issue with OpenJDK 8. I encountered lot of issues when testing on Windows machine with Maven and OpenJDK 8 combination.
2. There seems to be an issue with ca-certs which are missing due to which maven is unable to pull dependencies from the repository.
3. After installing JDK 8 from Oracle and configuring the system path variables to Oracle JDK 8, the build process succeeded.
4. Did not try other versions of OpenJDK. So it is recommend to install Oracle based Jdk 8 when testing on Windows.
5. All the other prerequisites remains the same.

## Build and run using command prompt
1. Go to the project directory where the pom.xml is located.
2. Build the project using the command
<br/>```mvn clean install```
3. Once the build is successful run the below command to run the application.
4. Now you need 2 instances of this application to test this application.
5. Open two terminals and run the below commands to run service 1 and service 2 respectively.
<br/>```java -jar target/gameofthree-1.0.jar --spring.config.name=application-service1```
<br/>```java -jar target/gameofthree-1.0.jar --spring.config.name=application-service2```

## Setting up the project on Mac/Debian based Linux
1. On Mac home brew can be used for installation and simiarly apt-get can be used on debian based linux.
2. Download Java JDK 8 and install it on the machine if not already installed.
3. Download and install Maven 3.5 or above if not already installed.
4. The project uses a library called lombok.
5. To integrate lombok support on Intellij, you can simply navigate to Settings->Plugin and search for Lombok.
6. Install the plugin and restart the IDE.
7. For eclipse, it would be necessary to download a jar and run once to configure the IDE. Please follow the instructions from the link below:
<br/>[lombok_plugin_eclipse](https://howtodoinjava.com/automation/lombok-eclipse-installation-examples/#lombok-eclipse)

## Build and run the application using IDE
1. Import the project as maven project.
2. Please follow the steps listed above to install the lombok plugin based on your IDE.
3. Build the application.
4. To run create 2 run-configurations for the application by selecting the Application class and clicking on Edit configurations under run.
5. Provide a name ex service1 and service2
6. Fill the Environment variables option with below values for service1 and service 2 instances respectively.
<br/>spring.config.name=application-service1
<br/>spring.config.name=application-service2
7. Now right click and run the instances service1 and service 2. 

## To test the application
1. After the application starts it can be tested on 2 tabs of a browser or any http clients using the below urls
2. for manually providing the value
<br/>http://localhost:8061/api/game/start?auto=false&manual=2000 
<br/>http://localhost:8061/api/game/start?auto=false
3. for automatic selection of value and start the game automatically use the below urls
<br/>http://localhost:8061/api/game/start?auto=true 
<br/>http://localhost:8061/api/game/start?auto=true
<br/>
4. The API documentation is based on Swagger2. It can be accessed from the below url:
<br/>http://localhost:8061/swagger-ui.html

### Further enhancements
1. Usage of a broker to fire events.
2. The current approach violates some aspects of command query separation principle(CQRS).
3. Make use of the entity relation better, instead of relying on a variable player's count.
4. Optimize Hibernate queries. Sometimes it's tricky under the hoods there are lots of queries generated, which could be optimized. 