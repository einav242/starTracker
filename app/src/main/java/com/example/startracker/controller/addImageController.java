package com.example.startracker.controller;

import android.graphics.Bitmap;

import com.example.startracker.entities.star;
import com.example.startracker.model.addImageModel;
import com.example.startracker.view.addImageView;

import java.util.List;

public class addImageController {
    addImageModel model;
    addImageView view;

    public addImageController(addImageView view, String id) {
        this.view = view;
        this.model = new addImageModel(this, id);
    }
    public void toast_controller(String s) {
        view.setToastView(s);
    }

    public void setProgressController(int num){
        view.setProgressView(num);
    }

    public void uploadFileController(Bitmap imageBitmap, String name){
        this.model.upload(imageBitmap,name);
    }

    public void uploadNewImageController(String path, String name) {
        model.uploadNewImage(path, name);
    }

    public void setImageController(String toString, String[] names) {
        view.getProcessedId(toString, names);
    }

    public void getIdController(String[] names, String url){
        this.view.getIdView(names, url);
    }


    public void addStarsController(String refId, List<star> stars, String imageUrl) {
        model.addStarsModel(refId, stars, imageUrl);
    }

    public void passPage(String imageUrl) {
        view.pass_to_newImage(imageUrl);
    }
}

