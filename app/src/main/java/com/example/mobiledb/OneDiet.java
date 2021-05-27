package com.example.mobiledb;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OneDiet {
    private double protein;
    private double carbo;
    private double fat;
    private ArrayList<String> foods_name = new ArrayList<>();

    public OneDiet(){
        this.protein = 0;
        this.carbo = 0;
        this.fat = 0;
    }

    public OneDiet(Food food) {
        this.protein = food.getProtein();
        this.carbo = food.getCarbo();
        this.fat = food.getFat();
        this.foods_name.add(food.getName());
    }

    public void plusFood(Food food){
        this.protein += food.getProtein();
        this.carbo += food.getCarbo();
        this.fat += food.getFat();
        this.foods_name.add(food.getName());
    }

    public ArrayList<String> getFoods_nameArrayList(){
        return foods_name;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbo() {
        return carbo;
    }

    public double getFat() {
        return fat;
    }

    public String returnDietToString(){
        String names = "";
        if(getFoods_nameArrayList().size() > 0) {
            for (int i = 0; i <getFoods_nameArrayList().size(); i++)
                names = names.concat(getFoods_nameArrayList().get(i) + "\n");
            names = names.substring(0, names.length() - 1);
        }
        return names;
    }

    public String returnNutritions(){
        return String.valueOf(getCarbo()) + ","
                + String.valueOf(getProtein()) + ","
                + String.valueOf(getFat()) + ",";
    }

    public String returnAll(){
        String names = "";
        if(getFoods_nameArrayList().size() > 0) {
            for (int i = 0; i <getFoods_nameArrayList().size(); i++){
                names.concat(getFoods_nameArrayList().get(i)+"/");
            }
        }
        return String.valueOf(getCarbo()) + ","
                + String.valueOf(getProtein()) + ","
                + String.valueOf(getFat()) + ","
                + names;
    }
}
