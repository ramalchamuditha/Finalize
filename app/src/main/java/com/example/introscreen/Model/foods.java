package com.example.introscreen.Model;

public class foods {
    private String Image;
    private String name;
    private String price;

    public foods() {
    }

    public foods(String image, String name, String price) {
        Image = image;
        this.name = name;
        this.price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
