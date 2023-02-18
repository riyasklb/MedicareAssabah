package com.example.medicareassabah.diet;

public class NutritionixDataModel {

    private String id, name, brand, cal, fat;

    public NutritionixDataModel(String id, String name, String brand, String cal, String fat) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.cal = cal;
        this.fat = fat;
    }

    public String getId(){
        return id;
    }

    public String getBrand(){
        return brand;
    }

    public String getCal(){
        return cal;
    }

    public String getFat(){
        return fat;
    }

    public String getName() {
        return name;
    }

}
