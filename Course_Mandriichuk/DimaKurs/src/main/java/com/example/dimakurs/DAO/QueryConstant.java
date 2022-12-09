package com.example.dimakurs.DAO;



public class QueryConstant {
    public static String selectSalad = "select id, Name from salad\n";
    public static String insertSalad = "insert into salad (Name)" +
            " values (?)";
    public static String selectVegetables = "select id, name, calories from vegetable";
    public static String insertVegetable = "insert into vegetable" +
            "(name,calories) values(?,?)";
    public static String insertVegetableSalad = "insert into vegetable_salad" +
            " (salad_id, vegetable_id, weight) values (?,?,?)";
    public static final String selectVegetableAndWeight = "select vegetable_id," +
            "name,calories,weight from vegetable join " +
                    "vegetable_salad on vegetable_id = vegetable.id where salad_id = ?";
    public static final String updateVegetableWeight = " update vegetable_salad " +
            "set weight = ? where vegetable_id = ? and salad_id = ?";
    public static String getLastIdCreated = "select @@identity";

    public static final String deleteVegetableFromSalad = "delete from vegetable_salad" +
            " where salad_id = ? and vegetable_id = ?";

    public static final String deleteVegetable = "delete from vegetable where " +
            "id = ?";
    public static final String deleteSalad = "delete from salad where " +
            "id = ?";
}