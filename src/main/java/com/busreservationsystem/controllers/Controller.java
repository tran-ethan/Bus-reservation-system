package com.busreservationsystem.controllers;

import com.busreservationsystem.App;
import javafx.event.ActionEvent;

import java.io.IOException;

public abstract class Controller {
    protected abstract void switchForm(ActionEvent event);
    protected void loadFXML(String fxml) {
        // Load FXML File
        try {
            App.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
