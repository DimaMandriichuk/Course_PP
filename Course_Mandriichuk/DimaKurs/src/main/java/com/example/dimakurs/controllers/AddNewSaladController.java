package com.example.dimakurs.controllers;

import com.example.dimakurs.DAO.DBManager;
import com.example.dimakurs.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddNewSaladController {

    @FXML
    private TextField saladName;

    @FXML
    void onAddSalad(ActionEvent event) {
        String name = saladName.getText();
        if(name.equals(""))
        {
            saladName.setText("ВИ ПОМИЛИЛИСЯ В ЖИТТІ");
            return;
        }
        DBManager.getInstance().insertSalad(name);
        SaladController.getInstance().initSaladBox();
        saladName.setText("");
    }

    @FXML
    void onBack(ActionEvent event) {
        HelloApplication.setScene(HelloApplication.getDisplayMenu());
    }

}
