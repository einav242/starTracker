package com.example.startracker.entities;

public class ImageRequest {
    private String ImagePath;

    public ImageRequest(String imagePath) {
        ImagePath = imagePath;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}
