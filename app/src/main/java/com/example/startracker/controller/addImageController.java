package com.example.startracker.controller;

import android.graphics.Bitmap;

import com.example.startracker.model.addImageModel;
import com.example.startracker.view.addImageView;

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
        view.pass_to_newImage(toString, names);
    }

    public void getIdController(String[] names){
        this.view.getIdView(names);
    }


}

