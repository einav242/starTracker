package com.example.startracker.controller;

import com.example.startracker.model.ActivityMenuModel;
import com.example.startracker.view.ActivityMenuView;

public class ActivityMenuController {
    ActivityMenuView view;
    ActivityMenuModel model;

    public ActivityMenuController(ActivityMenuView view, String id) {
        this.view = view;
        this.model = new ActivityMenuModel(this, id);
    }

    public void logOut_controller()
    {
        model.logOut_model();
    }
    public void getUserNameController(){

        model.getUserNameModel();
    }

    public void setNameController(String name){
        view.setUserName(name);
    }

    public void setToastController(String msg){
        view.setToastView(msg);
    }
}
