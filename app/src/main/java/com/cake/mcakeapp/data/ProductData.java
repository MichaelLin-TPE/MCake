package com.cake.mcakeapp.data;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductData implements Serializable {

    //品名
    private String name;
    //照片列表
    private ArrayList<String> imageUrlArray;
    //產品說明
    private String description;
    //原料說明
    private String materialDescription;
    //製造時間
    private int workMainDay;
    //熱量
    private int calorie;
    //是否上架
    private boolean isOnline;
    //是否促銷
    private boolean isOnSale;
    //現在的價格
    private int currentPrice;
    //特價的價格
    private int originalPrice;
    //蛋糕類別
    private String cakeType;
    //是否點擊愛心
    private boolean isCheckHeart;
    //是否點擊購物車
    private boolean isCheckCart;

    public ProductData(String name, ArrayList<String> imageUrlArray, String description, String materialDescription, int workMainDay, int calorie, boolean isOnline, boolean isOnSale, int currentPrice, int originalPrice, String cakeType) {
        this.name = name;
        this.imageUrlArray = imageUrlArray;
        this.description = description;
        this.materialDescription = materialDescription;
        this.workMainDay = workMainDay;
        this.calorie = calorie;
        this.isOnline = isOnline;
        this.isOnSale = isOnSale;
        this.currentPrice = currentPrice;
        this.originalPrice = originalPrice;
        this.cakeType = cakeType;
    }

    public ProductData(){

    }

    public boolean isCheckHeart() {
        return isCheckHeart;
    }

    public void setCheckHeart(boolean checkHeart) {
        isCheckHeart = checkHeart;
    }

    public boolean isCheckCart() {
        return isCheckCart;
    }

    public void setCheckCart(boolean checkCart) {
        isCheckCart = checkCart;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
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

    public ArrayList<String> getImageUrlArray() {
        return imageUrlArray;
    }

    public void setImageUrlArray(ArrayList<String> imageUrlArray) {
        this.imageUrlArray = imageUrlArray;
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
