package com.example.dimakurs.controllers;

import com.example.dimakurs.DAO.DBManager;
import com.example.dimakurs.HelloApplication;
import com.example.dimakurs.entity.Salad;
import com.example.dimakurs.entity.Vegetable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeSaladMenuController {

    private static Stage stage;

    private ObservableList<Vegetable> vegetables;

    @FXML
    private ComboBox<Vegetable> VegetableComboBox;

    @FXML
    private TextField weightTextField;

    private static ChangeSaladMenuController instance;

    public static ChangeSaladMenuController getInstance() {
        return instance;
    }

    @FXML
    void OnAddVeg(ActionEvent event) {
        double weight = 0;
        Vegetable vegetable = VegetableComboBox.getValue();
        Salad salad = SaladController.getInstance().selectedSalad;
        try {
            weight = Double.parseDouble(weightTextField.getText());
        }
        catch (NumberFormatException e)
        {
            weightTextField.setText("НАВІЩО ВИ ЦЕ НАКОЇЛИ?");
            return;
        }
        if(vegetable == null||salad==null)
        {
            weightTextField.setText("НАВІЩО ВИ ЦЕ НАКОЇЛИ?");
            return;
        }
        weightTextField.setText("");
        DBManager.getInstance().insertVegetableToSalad(salad.getId(),vegetable.getId(),weight);
    }

    public void setVegetableComboBox()
    {
        vegetables = FXCollections.observableArrayList(DBManager.getInstance().selectAllVegetables());
        VegetableComboBox.setItems(vegetables);
    }

    @FXML
    void initialize() {
        instance = this;
        setVegetableComboBox();
    }

    public static void openChangeMenu()
    {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("changeSaladMenu.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        stage.setTitle("Меню зміни салату");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void onExit(ActionEvent actionEvent) {
        stage.close();
    }
}
