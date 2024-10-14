# Bus Reservation Java App
The Bus Reservation System is a software application designed to streamline the process of booking and managing bus tickets for both customers and administrators. It is a user-friendly and efficient software application developed to simplify the process of reserving bus seats, managing bookings, and maintaining user profiles. The system provides an intuitive GUI designed using JavaFX, offering a seamless experience for both administrators and clients. It utilizes the Jackson library for data storage in JSON files, ensuring reliable and secure data management.
The primary goal of the software is to offer a convenient and reliable platform where passengers can effortlessly search for available bus routes, check seat availability, and make secure reservations from the comfort of their homes or on the go. The system also provides bus operators with a comprehensive management tool to efficiently manage their buses, schedules, and bookings.

https://github.com/user-attachments/assets/d775529f-8e6a-4155-a4b6-e6abfa0e6f93

## Project Report
The full range of features with in-depth explanations can be found in the [project report](Project-Report.pdf)
## Features
### Client Features
- Login and Sign-up
    - Clients can create an account and log in
    - Profile editing for updating personal information (name, password, email)
- Bus Selection
    - Search for buses by source, destination, date, and bus ID
    - Sort buses by ticket price, date, or bus ID (ascending/descending)
- Seat Selection
    - View available and occupied seats
    - Select multiple seats, with real-time fare calculation
- View Bookings
    - Manage and view booked seats, with the option to cancel bookings for refunds
- Edit Profile
    - Modify account information (username, password, email)
- Manage Balance
    - Deposit and withdraw funds within their account
### Admin Features
- Manage buses
    - Add, edit, and remove buses
    - View bus information including ID, seating capacity, origin/destination, and schedules
    - Search and sort buses by various attributes (price, date)
- Manage clients
    - View and edit client details (username, balance, email)
    - Search and sort clients by balance or username
- Manage Bookings
    - View, update, or cancel client bookings
    - Search and sort bookings by price, date, or bus details
- Edit Client Profiles
    - Modify client account details such as username and password
- Edit Bookings
    - Modify existing bookings (seat number, schedule, etc.)
- Admin Profile Management
    - Update admin account information (username, email, password)

## Usage
To run the Bus Reservation Java App, you have three options:

### Using the pre-compiled JAR File
1. Open a terminal or command prompt.

2. Navigate to the root directory of the project.

3. Double-click on the JAR file to execute it, or execute the following command at the root directory:
    ```shell
    java -jar BusReservationSystem.jar
    ```
   This will start the application. Make sure the SDK is properly installed and configured to use Java JDK version 18.
   To verify, run `java -version` in terminal. If using Windows, configure environment variables `PATH` to the JDK 
   version 18 bin location, and `JAVA_HOME` to JDK location.

### Using IDE

1. Open your selected IDE

2. Open the project as a Maven project (in IntelliJ, open pom.xml), and run the `App.main()`. 
   Make sure the SDK is configured to Java Development Kit (JDK) version 18.
   
### Using JavaFX Maven Plugin
1. Open a terminal or command prompt.

2. Navigate to the root directory of the project.

3. Execute the following command:
    ```shell
    mvnw.cmd javafx:run
    ```
