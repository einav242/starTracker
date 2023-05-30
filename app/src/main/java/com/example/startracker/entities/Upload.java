package com.example.startracker.entities;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String realDataId;
    private String storageId;
    private String coordinates;
    private String [] stars;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String imageUrl, String realDataId, String storageId, String coordinates) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mImageUrl = imageUrl;
        this.realDataId = realDataId;
        this.storageId = storageId;
        this.coordinates = coordinates;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getRealDataId() {
        return realDataId;
    }

    public void setRealDataId(String realDataId) {
        this.realDataId = realDataId;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
