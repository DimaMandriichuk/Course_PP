package com.example.dimakurs.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.*;


public class Salad {
    private SimpleIntegerProperty id;
    private SimpleStringProperty Name;
    private List<Vegetable> vegetableList = new ArrayList<>();
    private Map<Vegetable,Double> vegetableWeightMap = new HashMap<>();
    public Salad()
    {

    }
    public Salad(Integer id, String name) {
        this.id = new SimpleIntegerProperty(id);
        Name = new SimpleStringProperty( name);
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
        return Name.get();
    }

    public SimpleStringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public List<Vegetable> getVegetableList() {
        return vegetableList;
    }

    public void setVegetableList(List<Vegetable> vegetableList) {
        this.vegetableList = vegetableList;
    }

    public Map<Vegetable, Double> getVegetableWeightMap() {
        return vegetableWeightMap;
    }

    public void setVegetableWeightMap(Map<Vegetable, Double> vegetableWeightMap) {
        this.vegetableWeightMap = vegetableWeightMap;
    }

    public void printVegetables() {
        for (Vegetable vegetable : vegetableList)
            System.out.println("Ім'я: " + vegetable.getName() + "\nКалорій на 1 кг: " + vegetable.getCalories() +
                    "\nМаса: " + vegetableWeightMap.get(vegetable.getId()) + " кг");
    }



    public void sortVegetablesByName() {

        vegetableList.sort(Comparator.comparing(vegetable -> vegetable.getName()));
    }

    public void sortVegetablesByCalories() {
        vegetableList.sort(Comparator.comparing(vegetable -> vegetable.getCalories()));
    }

    public void sortVegetablesByWeight() {
        vegetableList.sort(Comparator.comparing(vegetable -> vegetableWeightMap.get(vegetable.getId())));
    }

    @Override
    public String toString() {
        return Name.get();
    }
}
