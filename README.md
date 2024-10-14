# Bus Reservation Java App

## Project Report
[Click to open project report](Project-Report.pdf)

## Usage
To run the Bus Reservation Java App, you have three options:
### Option 1: Run the Pre-compiled JAR File
1. Open a terminal or command prompt.

2. Navigate to the root directory of the project.

3. Double-click on the JAR file to execute it, or execute the following command at the root directory:
    ```shell
    java -jar BusReservationSystem.jar
    ```
   This will start the application. Make sure the SDK is properly installed and configured to use Java JDK version 18.
   To verify, run `java -version` in terminal. If using Windows, configure environment variables `PATH` to the JDK 
   version 18 bin location, and `JAVA_HOME` to JDK location.

### Option 2: Run the App Class in an IDE

1. Open your selected IDE

2. Open the project as a Maven project (in IntelliJ, open pom.xml), and run the `App.main()`. 
   Make sure the SDK is configured to Java Development Kit (JDK) version 18.
   
### Option 3:  Run with JavaFX Maven Plugin
1. Open a terminal or command prompt.

2. Navigate to the root directory of the project.

3. Execute the following command:
    ```shell
    mvnw.cmd javafx:run
    ```
