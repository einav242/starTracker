package com.example.startracker.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.startracker.controller.addImageController;
import com.example.startracker.entities.Upload;
import com.example.startracker.entities.star;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.List;

public class addImageModel {
    addImageController controller;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private UploadTask uploadTask;
    String id;

    public addImageModel(addImageController controller,String id) {
        this.id = id;
        this.controller = controller;
        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads").child(id);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Uploads");

    }

    public void upload(Bitmap imageBitmap, String name){
        if ( uploadTask != null && uploadTask.isInProgress()) {
            controller.toast_controller("Upload in progress");
        } else {
            uploadFile(imageBitmap, name);
        }
    }

    public void uploadFile(Bitmap imageBitmap, String name) {
        if (imageBitmap!=null) {
            String names[] = new String[2];
            long time = new Date().getTime();
            StorageReference imageRef = mStorageRef.child(time + "");
            names[0] = time + "";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();
            uploadTask = imageRef.putBytes(imageData);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    controller.setProgressController(0);
                                }
                            }, 500);
                            controller.toast_controller("Upload successful");
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String uploadId = mDatabaseRef.push().getKey();
                                    String url = uri.toString();
                                    Upload upload = new Upload(name,
                                            url,uploadId,names[0],"");
                                    mDatabaseRef.child(uploadId).setValue(upload);
                                    System.out.println("url= "+upload.getImageUrl());
                                    names[1]= uploadId;
                                    controller.getIdController(names, upload.getImageUrl());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            controller.toast_controller(e.getMessage());
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            controller.setProgressController((int)progress);
                        }
                    });
        } else {
            controller.toast_controller("No file selected");
        }
    }

    public void uploadNewImage(String imagePath, String ImageName, String coordinates, String storageId, String uploadId){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(id).child("processed");
        String names[] = new String[2];
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("processed").
                child(id).child(storageId);
        names[0] = storageId;
        storageRef.putFile(Uri.fromFile(new File(imagePath)))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        controller.setProgressController(0);
                                    }
                                }, 500);
                                String name = ImageName;
                                Upload upload = new Upload(name,
                                        uri.toString(),uploadId,names[0], coordinates);
                                names[1]= uploadId;
                                databaseRef.child(uploadId).setValue(upload);
                                controller.setImageController(uri.toString(), names);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void addStarsModel(String refId, List<star> stars, String imageUrl) {
       FirebaseDatabase.getInstance().getReference("Users").child(id).child("processed").
                child(refId).child("stars").setValue(stars).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
       controller.passPage(imageUrl);

    }
}
