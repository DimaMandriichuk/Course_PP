package com.example.dimakurs.controllers;

import com.example.dimakurs.DAO.DBManager;
import com.example.dimakurs.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddNewVegetableController {
    @FXML
    private Button addNewVegetable;

    @FXML
    private Button back;

    @FXML
    private TextField VegetableNameField;

    @FXML
    private TextField WeightVolumeField;

    @FXML
    void onAddNewSort(ActionEvent event) {
        String name = VegetableNameField.getText();
        double weight;
        try {
            weight = Double.parseDouble( WeightVolumeField.getText());
        }
        catch (NumberFormatException e)
        {

            WeightVolumeField.setText("ВИ ПОМИЛИЛИСЯ В ЖИТТІ");
            return;
        }
        if(name.equals(""))
        {
            VegetableNameField.setText("ВИ ПОМИЛИЛИСЯ В ЖИТТІ");
            return;
        }
        DBManager.getInstance().insertVegetable(weight,name);
        VegetableNameField.setText("");
        WeightVolumeField.setText("");
        ChangeSaladMenuController.getInstance().setVegetableComboBox();
    }

    @FXML
    void onBack(ActionEvent event) {
        HelloApplication.setScene(HelloApplication.getDisplayMenu());
    }
}
