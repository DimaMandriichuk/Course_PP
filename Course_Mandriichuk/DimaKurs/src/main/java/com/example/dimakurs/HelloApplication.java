package com.example.dimakurs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage mainStage;
    private static Scene mainMenu;
    private static Scene displayMenu;
    private static Scene addNewVegetableMenu;
    private static Scene addNewSaladMenu;
    private static Scene changeSaladMenu;
    private static Scene deleteMenu;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMenuView.fxml"));
        mainMenu = new Scene(fxmlLoader.load());
        stage.setTitle("META SALAD");
        stage.setScene(mainMenu);
        stage.show();
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("saladMenu.fxml"));
        displayMenu  = new Scene(fxmlLoader.load());
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addNewVegetableView.fxml"));
        addNewVegetableMenu  = new Scene(fxmlLoader.load());
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addNewSaladView.fxml"));
        addNewSaladMenu  = new Scene(fxmlLoader.load());
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("changeSaladMenu.fxml"));
        changeSaladMenu  = new Scene(fxmlLoader.load());


    }

    public static Scene getDisplayMenu() {
        return displayMenu;
    }

    public static Scene getDeleteMenu() {
        return deleteMenu;
    }

    public static Scene getMainMenu() {
        return mainMenu;
    }

    public static Scene getAddNewVegetableMenu() {
        return addNewVegetableMenu;
    }

    public static Scene getChangeSaladMenu() {
        return changeSaladMenu;
    }

    public static Scene getAddNewSaladMenu() {
        return addNewSaladMenu;
    }

    public static void  setScene(Scene scene)
    {
        mainStage.setScene(scene);
    }
    public static Stage getMainStage() {
        return mainStage;
    }

    public static void main(String[] args) {
        launch();
    }
}