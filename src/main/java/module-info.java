module com.busreservationsystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.busreservationsystem to javafx.fxml;
    exports com.busreservationsystem;
    exports com.busreservationsystem.controllers;
    opens com.busreservationsystem.controllers to javafx.fxml;
}