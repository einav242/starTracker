package com.example.startracker.model;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.startracker.controller.ImagesActivityController;
import com.example.startracker.entities.Upload;
import com.example.startracker.view.ImageAdapter;
import com.example.startracker.view.ImagesActivityView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ImagesActivityModel {
    ImagesActivityController controller;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private StorageReference mStorageRef;
    String id;

    public ImagesActivityModel(ImagesActivityController controller, String id) {
        this.controller = controller;
        this.id = id;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(id).child("processed");
        mStorageRef = FirebaseStorage.getInstance().getReference("processed").child(id);
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

    public void deleteItemModel(String realDataId, String storageId) {
        mDatabaseRef.child(realDataId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        mStorageRef.child(storageId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
        FirebaseDatabase.getInstance().getReference("Users").child(id).child("Uploads").child(realDataId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        FirebaseStorage.getInstance().getReference("Uploads").child(id).child(storageId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
        controller.passThisPage();
    }
}
