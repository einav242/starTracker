package com.example.startracker.model;

import androidx.annotation.NonNull;

import com.example.startracker.controller.ActivityMenuController;
import com.example.startracker.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityMenuModel {
    ActivityMenuController controller;
    public String userName;
    private FirebaseAuth fAuth;
    private DatabaseReference reference;
    String id;

    public ActivityMenuModel(ActivityMenuController controller, String id) {
        this.id = id;
        this.controller = controller;
        this.fAuth = FirebaseAuth.getInstance();
        this.reference = FirebaseDatabase.getInstance().getReference("Users");
    }

    public void logOut_model() {
        FirebaseAuth.getInstance().signOut();
    }

    public void getUserNameModel() {
        FirebaseDatabase.getInstance().getReference().child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                controller.setNameController(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
