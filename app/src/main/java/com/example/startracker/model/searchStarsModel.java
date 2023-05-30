package com.example.startracker.model;

import androidx.annotation.NonNull;

import com.example.startracker.controller.searchStarsController;
import com.example.startracker.entities.Upload;
import com.example.startracker.entities.star;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class searchStarsModel {
    searchStarsController controller;
    String id;
    String ImageID;
    boolean found;

    private DatabaseReference mDatabaseRef;

    public searchStarsModel(searchStarsController controller, String id, String imageID) {
        this.controller = controller;
        this.id = id;
        this.ImageID =imageID;
        this.mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(id).child("processed").
                child(ImageID).child("stars");
        this.found = false;
    }

    public void searchModel(String s) {
        this.mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    star st = dataSnapshot.getValue(star.class);
                    if (st.getId().equals(s)){
                        found = true;

                        controller.setStar(st);
                    }
                }
                if(found == false){
                    controller.toast_controller(" The ID does not exist");
                }
                else {
                    found = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void getCoordinatesModel() {
        FirebaseDatabase.getInstance().getReference("Users").child(id).child("processed").
                child(ImageID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Upload upload = snapshot.getValue(Upload.class);
                        if(upload!=null){
                            controller.setCoordinatesController(upload.getCoordinates());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void saveImageModel(String storageId){
        StorageReference imageRef =  FirebaseStorage.getInstance().getReference().child("Uploads").child(id).
                child(storageId);

        File localFile = new File("/storage/emulated/0/Android/data/com.example.startracker/files/stars.jpg");

        imageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                controller.callFunctionController();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }


}
