package com.example.startracker.entities;

import java.io.Serializable;

import retrofit2.Call;

public class ImageResponse implements Serializable {
    private String stars_id;


    public String getStars_id() {
        return stars_id;
    }

    public void setStars_id(String stars_id) {
        this.stars_id = stars_id;
    }
}
