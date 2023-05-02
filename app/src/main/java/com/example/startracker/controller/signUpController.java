package com.example.startracker.controller;

import android.text.TextUtils;

import com.example.startracker.model.signUpModel;
import com.example.startracker.view.signUpView;

public class signUpController {
    signUpView view;
    signUpModel model;

    public signUpController(signUpView view) {
        this.view = view;
        model = new signUpModel(this);
    }

    public void registerUserController(String txtName, String txtEmail, String txtPassword) {
        if (TextUtils.isEmpty(txtName)
                || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
            view.setToastView("Empty credentials!");
        } else if (txtPassword.length() < 6){
            view.setToastView("Password too short!");
        } else {
            model.registerUser(txtName,txtEmail,txtPassword);
        }
    }

    public void toast_controller(String s) {
        view.setToastView(s);
    }

    public void passPageController(){
        view.passPage();
    }
}
