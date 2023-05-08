package com.example.startracker.model;

import android.view.View;
import android.widget.Toast;

import com.example.startracker.controller.ImagesActivityController;
import com.example.startracker.entities.Upload;
import com.example.startracker.view.ImageAdapter;
import com.example.startracker.view.ImagesActivityView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivityModel {
    ImagesActivityController controller;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;

    public ImagesActivityModel(ImagesActivityController controller, String id) {
        this.controller = controller;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Uploads");
        mUploads = new ArrayList<>();
    }

     public void getImages(){
         mDatabaseRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                     Upload upload = postSnapshot.getValue(Upload.class);
                     mUploads.add(upload);
                 }

                 controller.setAdapterController(mUploads);
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {
                 controller.setToastController(databaseError.getMessage());
             }
         });
     }
}
