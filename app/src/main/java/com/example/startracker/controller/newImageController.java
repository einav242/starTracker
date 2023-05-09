package com.example.startracker.controller;

import com.example.startracker.model.newImageModel;
import com.example.startracker.view.newImageView;

public class newImageController {
    newImageModel model;
    newImageView view;

    public newImageController(newImageView view, String id) {
        this.view = view;
        this.model = new newImageModel(this, id);
    }
    public void deleteImageController( String refId, String refProcessedId, String storageId,String storageProcessedId, int flag){
        this.model.deleteImageModel(refId,refProcessedId,storageId,storageProcessedId, flag);
    }

    public void passPageMenuController(){
        view.passPageMenu();
    }
    public void passPageAddController(){
        view.passPageAdd();
    }
}
