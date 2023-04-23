package com.example.snake;

import java.util.Random;

public class Food {
    public enum FoodType {
        NORMAL,
        SPEED_UP,
    }

    private int x;
    private int y;
    private FoodType type;

    // Constructor that takes in the initial x, y coordinates, and the type of the food
    public Food(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
    public static FoodType randomFoodType() {
        int pick = new Random().nextInt(FoodType.values().length);
        return FoodType.values()[pick];
    }


    // Getter method for x coordinate
    public int getX() {
        return x;
    }

    // Getter method for y coordinate
    public int getY() {
        return y;
    }

    // Setter method for x coordinate
    public void setX(int x) {
        this.x = x;
    }

    // Setter method for y coordinate
    public void setY(int y) {
        this.y = y;
    }

    // Getter method for food type
    public FoodType getType() {
        return type;
    }

    // Setter method for food type
    public void setType(FoodType type) {
        this.type = type;
    }


}
