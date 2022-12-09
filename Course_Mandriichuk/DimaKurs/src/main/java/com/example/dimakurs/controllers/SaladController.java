package com.example.dimakurs.controllers;

import com.example.dimakurs.DAO.DBManager;
import com.example.dimakurs.DAO.QueryConstant;
import com.example.dimakurs.HelloApplication;
import com.example.dimakurs.customCells.PromptCell;
import com.example.dimakurs.entity.Salad;
import com.example.dimakurs.entity.Vegetable;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.Map;
import java.util.stream.Collectors;

public class SaladController {

    public ObservableList<Salad> salads;
    public TableView<Vegetable> tableId;
    Salad selectedSalad = null;
    ObservableList<Vegetable> vegetables;

    private  static  SaladController instance;

    public static SaladController getInstance() {
        return instance;
    }
    @FXML
    private TableColumn<Vegetable, Double> caloriesCol;

    @FXML
    private ComboBox<Salad> chooseSaladCB;

    @FXML
    private TextField fromCal;

    @FXML
    private TableColumn<Vegetable, Integer> idCol;

    @FXML
    private TableColumn<Vegetable, String> nameCol;

    @FXML
    private TextField toCal;

    @FXML
    private Label totalCalories;

    @FXML
    private TableColumn<Vegetable, Double> weightCol;

    @FXML
    void onBack(ActionEvent event) {
        HelloApplication.setScene(HelloApplication.getMainMenu());
    }


    @FXML
    void onCreateNewVegetable(ActionEvent event) {
        HelloApplication.setScene(HelloApplication.getAddNewVegetableMenu());
    }

    @FXML
    void onCreateSalad(ActionEvent event) {
        HelloApplication.setScene(HelloApplication.getAddNewSaladMenu());
    }


    @FXML
    void onSearchVegetable(ActionEvent event) {
        double from = Double.parseDouble(fromCal.getText());
        double to = Double.parseDouble(toCal.getText());
        ObservableList<Vegetable> vegetableObservableList;
        if(selectedSalad ==null)
            vegetableObservableList = vegetables;
        else
            vegetableObservableList = FXCollections.observableArrayList(selectedSalad.getVegetableWeightMap().keySet());
        vegetables = FXCollections.observableArrayList(vegetableObservableList.stream().
                filter(x->x.getCalories()>=from&&x.getCalories()<=to).
                collect(Collectors.toList()));
        tableId.setItems(vegetables);
        tableId.refresh();
    }

    @FXML
    void onShowAllVegetables(ActionEvent event) {
        selectedSalad = null;
        chooseSaladCB.setValue(null);
        vegetables = FXCollections.observableArrayList(DBManager.getInstance().selectAllVegetables());
        tableId.setItems(vegetables);
        tableId.refresh();
    }


    public void initTable()
    {
        Callback<TableColumn<Vegetable,Double>, TableCell<Vegetable,Double>> cellFactory =
                p -> {
                    TextFieldTableCell<Vegetable, Double> tx = new TextFieldTableCell<>();
                    tx.setConverter(new StringConverter<>() {
                        @Override
                        public String toString(Double aDouble) {
                            return aDouble.toString();
                        }
                        @Override
                        public Double fromString(String s) {
                            return Double.valueOf(s);
                        }
                    });
                    return tx;
                };
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        caloriesCol.setCellValueFactory(new PropertyValueFactory<>("calories"));
        caloriesCol.setCellFactory(cellFactory);
        weightCol.setCellFactory(cellFactory);
        weightCol.setCellValueFactory(CellData-> new ObservableValueBase<>() {
            @Override
            public Double getValue() {
                if (selectedSalad == null)
                    return 0.0;
                return selectedSalad.getVegetableWeightMap().get(CellData.getValue());
            }
        });
        vegetables = FXCollections.observableArrayList( DBManager.getInstance().selectAllVegetables());
        tableId.setItems(vegetables);
        tableId.refresh();
    }

    public void initSaladBox()
    {
        salads = FXCollections.observableArrayList(DBManager.getInstance().selectSalads());
        chooseSaladCB.setButtonCell(new PromptCell<>(chooseSaladCB.getPromptText()));
        chooseSaladCB.setItems(salads);
    }



    @FXML
    void initialize() {
        instance = this;
        initTable();
        initSaladBox();

    }

    public void onShowSaladVegetables(ActionEvent actionEvent) {
        selectedSalad = chooseSaladCB.getValue();
        Map<Vegetable,Double> map = DBManager.getInstance().selectVegetableWeightInSalad(selectedSalad.getId());
        selectedSalad.setVegetableWeightMap(map);
        tableId.setItems(FXCollections.observableArrayList(selectedSalad.getVegetableWeightMap().keySet()));
        totalCalories.setText(String.valueOf(map.entrySet().stream().mapToDouble(x->x.getKey().getCalories()*x.getValue()).sum()));
        tableId.refresh();
    }

    public void onChangeSalad(ActionEvent actionEvent) {
        ChangeSaladMenuController.openChangeMenu();
    }

    public void onChooseSalad(ActionEvent actionEvent) {
        selectedSalad = chooseSaladCB.getValue();
    }

    public void onWeightEditCommit(TableColumn.CellEditEvent<Vegetable, Double> vegetableDoubleCellEditEvent) {
        Double weight = vegetableDoubleCellEditEvent.getNewValue();
        if(weight<0||selectedSalad==null)
        {
            vegetableDoubleCellEditEvent.consume();
            tableId.refresh();
            return;
        }
        DBManager.getInstance().updateWeight(selectedSalad.getId(),
                vegetableDoubleCellEditEvent.getRowValue().getId(),
                vegetableDoubleCellEditEvent.getNewValue());
        onShowSaladVegetables(new ActionEvent());
    }

    public void onDeleteVegetableFromSalad(ActionEvent actionEvent) {
       DeleteVegetableFromSaladViewController.openDeleteMenu();
    }

    public void onDeleteSalad(ActionEvent actionEvent) {
        DBManager.getInstance().deleteById(selectedSalad.getId(), QueryConstant.deleteSalad);
        initSaladBox();
        onShowAllVegetables(actionEvent);
    }
}
