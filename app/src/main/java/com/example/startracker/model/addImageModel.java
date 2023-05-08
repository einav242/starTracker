package com.example.startracker.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.startracker.controller.addImageController;
import com.example.startracker.entities.Upload;
import com.example.startracker.view.addImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class addImageModel {
    addImageController controller;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private UploadTask uploadTask;

    public addImageModel(addImageController controller,String id) {
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
            long time = new Date().getTime();
            StorageReference imageRef = mStorageRef.child(time + "");
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
                                    Upload upload = new Upload(name,
                                            uri.toString());
                                    String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadId).setValue(upload);
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
}
