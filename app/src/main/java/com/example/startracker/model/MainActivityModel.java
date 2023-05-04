package com.example.startracker.model;

import androidx.annotation.NonNull;

import com.example.startracker.controller.MainActivityController;
import com.example.startracker.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivityModel {
    private MainActivityController controller;
    FirebaseAuth mAuth;
    private DatabaseReference mData;

    public MainActivityModel(MainActivityController controller) {
        this.controller = controller;
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
    }

    public void login(String username, String passwordE) {
        mAuth.signInWithEmailAndPassword(username, passwordE).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    System.out.println("hi!!!!!!!!!!!!!");
                    checkUserAccesLevel(task.getResult().getUser().getUid());
                } else {
                    controller.toast_controller("Log in Error: " + task.getException().getMessage());
                }
            }

        });
    }

    private void checkUserAccesLevel(String uid) {
        mData = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    System.out.println("hi222222222222222222");
                    User user =  snapshot.getValue(User.class);
                    controller.passActivity_controller(user);
                }
                else{
                    controller.toast_controller("Log in Error: User Not Exist at that section");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
