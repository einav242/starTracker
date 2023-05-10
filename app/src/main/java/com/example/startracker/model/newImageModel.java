package com.example.startracker.model;

import androidx.annotation.NonNull;

import com.example.startracker.controller.newImageController;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class newImageModel {
    newImageController controller;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRefProcessed;
    private DatabaseReference mDatabaseRefProcessed;
    private String id;

    public newImageModel(newImageController controller, String id) {
        this.controller = controller;
        this.id = id;
        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads").child(id);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Uploads");
        mStorageRefProcessed = FirebaseStorage.getInstance().getReference("processed").child(id);
        mDatabaseRefProcessed = FirebaseDatabase.getInstance().getReference("Users").child(id).child("processed");
    }

    public void deleteImageModel( String refId, String refProcessedId, String storageId,String storageProcessedId, int flag, int flag1){
        if(flag1==0){
            mDatabaseRef.child(refId).removeValue()
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
        }
        mDatabaseRefProcessed.child(refProcessedId).removeValue()
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
        if(flag1==0){
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
        }
        mStorageRefProcessed.child(storageProcessedId).delete()
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
        if(flag == 0) {
            controller.passPageMenuController();
        }else {
            controller.passPageAddController();
        }

    }
}
