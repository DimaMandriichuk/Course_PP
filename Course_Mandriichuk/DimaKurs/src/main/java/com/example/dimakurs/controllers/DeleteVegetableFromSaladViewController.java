package com.example.dimakurs.controllers;

import com.example.dimakurs.DAO.DBManager;
import com.example.dimakurs.DAO.QueryConstant;
import com.example.dimakurs.HelloApplication;
import com.example.dimakurs.customCells.PromptCell;
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
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteVegetableFromSaladViewController {
    static Stage stage;
    ObservableList<Vegetable> vegetables;
     @FXML
    private ComboBox<Vegetable> chooseComboBox;

    @FXML
    void onBack(ActionEvent event) {
        stage.close();
    }


    @FXML
    void onDelete(ActionEvent event) {
        Vegetable vegetable = chooseComboBox.getValue();
        Salad salad = SaladController.getInstance().selectedSalad;
        if(vegetable== null) {
            onBack(event);
            return;
        }
        if(salad==null) {
            DBManager.getInstance().deleteById(vegetable.getId(), QueryConstant.deleteVegetable);
            chooseComboBox.setValue(null);
            return;
        }
        DBManager.getInstance().deleteVegetableFromSalad(salad.getId(),vegetable.getId());
        chooseComboBox.setValue(null);
    }

    public static void openDeleteMenu()
    {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("deleteVegetableFromSaladView.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        stage.setTitle("Меню видалення овочів");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void initialize() {
        if(SaladController.getInstance().selectedSalad!=null)
            vegetables = FXCollections.observableArrayList(SaladController.
                getInstance().selectedSalad.getVegetableWeightMap().keySet());
        else
            vegetables = SaladController.getInstance().vegetables;
        chooseComboBox.setButtonCell(new PromptCell<>(chooseComboBox.getPromptText()));
        chooseComboBox.setItems(vegetables);
    }
}
