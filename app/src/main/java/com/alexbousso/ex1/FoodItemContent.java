package com.alexbousso.ex1;

import java.io.Serializable;

public class FoodItemContent implements Serializable {
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
