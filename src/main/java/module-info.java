module com.busreservationsystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.busreservationsystem to javafx.fxml;
    exports com.busreservationsystem;
}