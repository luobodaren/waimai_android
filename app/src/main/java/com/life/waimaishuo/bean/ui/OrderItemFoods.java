package com.life.waimaishuo.bean.ui;

public class OrderItemFoods {

    String imgUrl;
    String foodName;
    String ingredients;
    String foodCount;
    String price;

    public OrderItemFoods(String imgUrl, String foodName, String ingredients, String foodCount, String price) {
        this.imgUrl = imgUrl;
        this.foodName = foodName;
        this.ingredients = ingredients;
        this.foodCount = foodCount;
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(String foodCount) {
        this.foodCount = foodCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
