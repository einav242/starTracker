package com.example.startracker.controller;

import com.example.startracker.entities.Upload;
import com.example.startracker.model.ImagesActivityModel;
import com.example.startracker.view.ImagesActivityView;

import java.util.List;

public class ImagesActivityController {
    ImagesActivityView view;
    ImagesActivityModel model;

    public ImagesActivityController(ImagesActivityView view,String id, int flag) {
        this.view = view;
        this.model = new ImagesActivityModel(this, id, flag);
    }
    public void setAdapterController(List<Upload> mUploads){
        this.view.addAdapter(mUploads);
    }


    public void setToastController(String message) {
        this.view.setToastView(message);
    }

    public void getImagesController() {
        model.getImages();
    }

    public void deleteItemController(String realDataId, String storageId) {
        model.deleteItemModel(realDataId, storageId);
    }

    public void passThisPage() {
        view.passThisPageView();
    }
}
