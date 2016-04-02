package com.alexbousso.ex1;

public class FoodItemContent {
    private String foodName;
    private int foodImageId;

    public FoodItemContent(String foodName, int foodImageId) {
        this.foodName = foodName;
        this.foodImageId = foodImageId;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodImageId() {
        return foodImageId;
    }
}
