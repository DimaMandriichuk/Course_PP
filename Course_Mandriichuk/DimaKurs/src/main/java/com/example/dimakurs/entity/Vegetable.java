package com.example.dimakurs.entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Vegetable {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty calories;

    public Vegetable()
    {

    }
    public Vegetable(int id, String  name, Double calories) {
        this.id =  new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.calories = new SimpleDoubleProperty(calories);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getCalories() {
        return calories.get();
    }

    public SimpleDoubleProperty caloriesProperty() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories.set(calories);
    }

    @Override
    public String toString() {
        return name.get();
    }
}
