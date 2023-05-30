package com.example.startracker.controller;

import com.example.startracker.entities.star;
import com.example.startracker.model.searchStarsModel;
import com.example.startracker.view.searchStarsView;

public class searchStarsController {
    searchStarsView view;
    searchStarsModel model;

    public searchStarsController(searchStarsView view, String id, String imageID) {
        this.view = view;
        this.model = new searchStarsModel(this,id,imageID);
    }

    public void searchController(String s) {
        model.searchModel(s);
    }

    public void setStar(star s) {
        view.setStarView(s);
    }
    public void toast_controller(String s) {
        view.setToastView(s);
    }

    public void getCoordinatesController() {
        model.getCoordinatesModel();
    }

    public void setCoordinatesController(String coordinates) {
        view.setCoordinatesView(coordinates);
    }


    public void callFunctionController() {
        view.callFunctionView();
    }

    public void saveImageController(String storageId) {
        model.saveImageModel(storageId);
    }
}
