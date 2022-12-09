package com.example.dimakurs.DAO;

import com.example.dimakurs.HelloApplication;
import com.example.dimakurs.entity.Salad;
import com.example.dimakurs.entity.Vegetable;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;


public class DBManager {
    private static DBManager instance;
    private final Connection connection;
    private final String propertiesFile = "DBConfig.properties";

    private DBManager ()
    {
        List<Integer> list;
        InputStream stream = HelloApplication.class.getResourceAsStream(propertiesFile);
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String URL = properties.getProperty("dbURL");
        String USERNAME = properties.getProperty("dbUser");
        String PASSWORD = properties.getProperty("dbPassword");

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {

            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static DBManager getInstance() {
        if(instance==null)
            instance = new DBManager();
        return instance;
    }


    private void updateCoffeeNum(int id, double num, String query)
    {

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1,num);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public int insertVegetable(double weight, String name)
    {
        int id=0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(QueryConstant.insertVegetable);
            Statement statement = connection.createStatement()) {
            preparedStatement.setString(1,name);
            preparedStatement.setDouble(2,weight);
            preparedStatement.executeUpdate();
            ResultSet resultSet = statement.executeQuery(QueryConstant.getLastIdCreated);
            if(resultSet.next())
                id = resultSet.getInt(1);
            resultSet.close();
            if(id==0)
                throw new SQLException("Овоч НЕ ВСТАВИВСЯ");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return id;
    }

    public int insertSalad(String name)
    {
        int id = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(QueryConstant.insertSalad);
            Statement statement = connection.createStatement()) {
            preparedStatement.setString(1,name);
            preparedStatement.executeUpdate();
            ResultSet resultSet = statement.executeQuery(QueryConstant.getLastIdCreated);
            if(resultSet.next())
                id = resultSet.getInt(1);
            resultSet.close();
            if(id==0)
                throw new SQLException("Салат НЕ ВСТАВИВСЯ");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return id;
    }
    public void updateWeight(int salad_id, int vegetable_id, double newWeight)
    {
        try(PreparedStatement preparedStatement = connection.prepareStatement(QueryConstant.updateVegetableWeight);
            Statement statement = connection.createStatement()) {
            preparedStatement.setDouble(1,newWeight);
            preparedStatement.setInt(2,vegetable_id);
            preparedStatement.setInt(3,salad_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public List<Vegetable> selectAllVegetables() {
        List<Vegetable> vegetables = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QueryConstant.selectVegetables)) {
            int n =0;
            while (resultSet.next()) {
                vegetables.add(new Vegetable(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("calories")));
                n++;
            }
            if(n==0)
                System.out.println("Даних нема");
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return vegetables;
    }

    public List<Salad> selectSalads()
    {
        List<Salad> salads = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QueryConstant.selectSalad)) {
            int n =0;
            while (resultSet.next()) {
                salads.add(new Salad(resultSet.getInt("id"),
                        resultSet.getString("Name")));
                n++;
            }
            if(n==0)
                System.out.println("Даних нема");
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return salads;
    }

    public int insertVegetableToSalad(int salad_id, int veg_id,double weight) {
        int id = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(QueryConstant.insertVegetableSalad);
            Statement statement = connection.createStatement()) {
            preparedStatement.setInt(1, salad_id);
            preparedStatement.setInt(2, veg_id);
            preparedStatement.setDouble(3, weight);
            preparedStatement.executeUpdate();
            ResultSet resultSet = statement.executeQuery(QueryConstant.getLastIdCreated);
            if(resultSet.next())
                id = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public Map<Vegetable, Double> selectVegetableWeightInSalad(int salad_id)
    {
        Map<Vegetable,Double> vegetableDoubleMap = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement
                (QueryConstant.selectVegetableAndWeight)) {
            int n =0;
            preparedStatement.setInt(1,salad_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vegetableDoubleMap.put(new Vegetable(resultSet.getInt("vegetable_id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("calories")),
                        resultSet.getDouble("weight"));
                n++;
            }
            if(n==0)
                System.out.println("Даних нема");
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return vegetableDoubleMap;
    }

    public void deleteVegetableFromSalad(int salad_id, int vegetable_id)
    {
        try(PreparedStatement preparedStatement = connection.prepareStatement(QueryConstant.deleteVegetableFromSalad)) {
            preparedStatement.setInt(1,salad_id);
            preparedStatement.setInt(2,vegetable_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteById(int id, String query)
    {

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void close()
    {

        try {
            if(!connection.isClosed())
                connection.close();
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

}
