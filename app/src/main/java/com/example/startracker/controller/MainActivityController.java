package com.example.startracker.controller;

import android.text.TextUtils;

import com.example.startracker.entities.User;
import com.example.startracker.model.MainActivityModel;
import com.example.startracker.view.MainActivityView;

public class MainActivityController {
    MainActivityView view;
    MainActivityModel model;

    public MainActivityController(MainActivityView view) {
        this.view = view;
        model = new MainActivityModel(this);
    }

    public void check_empty(String username, String passwordE) {
        if (TextUtils.isEmpty(username)) {
            view.noUserName();
        } else if (TextUtils.isEmpty(passwordE)) {
            view.noPassword();
        }
        else {
            model.login(username, passwordE);
        }
    }

    public void toast_controller(String s) {
        view.toast_view(s);
    }

    public void passActivity_controller(User user) {
        view.paasMenuActivity(user);
    }
}
