package com.example.dimakurs.controllers;

import com.example.dimakurs.HelloApplication;
import javafx.event.ActionEvent;

public class MainMenuController {
    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void onSaladMenu(ActionEvent actionEvent) {
        HelloApplication.setScene(HelloApplication.getDisplayMenu());
    }
}
