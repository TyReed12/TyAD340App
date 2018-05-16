package com.example.tyree.tyad340app;


public class Camera {

    private String imageUrl;

    private String description;

    public Camera(String description, String imageUrl) {
        this.imageUrl = imageUrl;
        this.description = description;

    }

    public String getDescription() {
        return description;
    }

    public String getImageurl() {
        return imageUrl;
    }


}


