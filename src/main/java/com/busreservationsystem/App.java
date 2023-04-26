package com.busreservationsystem;

import com.busreservationsystem.system.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.TreeSet;

public class App extends Application {

    private static Scene scene;
    private static Database database;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/LoginForm.fxml"));
            scene = new Scene(fxmlLoader.load(), 1024, 576);
            stage.setTitle("Bus Reservation System");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml) throws IOException {
        String filePath = String.format("views/%s.fxml", fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(filePath));
        scene.setRoot(fxmlLoader.load());
    }

    public static Database getDB() {
        return database;
    }

    public static void main(String[] args) {
        database = new Database("customers", "admins", "bookings", "buses");
        launch();
    }
}