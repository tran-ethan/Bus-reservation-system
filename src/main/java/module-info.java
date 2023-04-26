module com.busreservationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens com.busreservationsystem.system to javafx.base;
    opens com.busreservationsystem to javafx.fxml;
    exports com.busreservationsystem;
    exports com.busreservationsystem.controllers;
    exports com.busreservationsystem.system;
    opens com.busreservationsystem.controllers to javafx.fxml;
}