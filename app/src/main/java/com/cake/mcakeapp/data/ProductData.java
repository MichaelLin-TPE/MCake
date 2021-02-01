package com.cake.mcakeapp.data;

import java.util.ArrayList;

public class ProductData {

    private String name;

    private String imageUrl;

    private String description;

    private ArrayList<String> materialArray;

    private int workMainDay;

    private int calorie;

    private boolean isOnline;

    private boolean isOnSale;

    private int currentPrice;

    private int originalPrice;

    private String cakeType;

    public ArrayList<String> getMaterialArray() {
        return materialArray;
    }

    public void setMaterialArray(ArrayList<String> materialArray) {
        this.materialArray = materialArray;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWorkMainDay() {
        return workMainDay;
    }

    public void setWorkMainDay(int workMainDay) {
        this.workMainDay = workMainDay;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public void setOnSale(boolean onSale) {
        isOnSale = onSale;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCakeType() {
        return cakeType;
    }

    public void setCakeType(String cakeType) {
        this.cakeType = cakeType;
    }
}
