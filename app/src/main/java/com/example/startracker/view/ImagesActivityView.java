package com.example.startracker.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.startracker.R;
import com.example.startracker.controller.ImagesActivityController;
import com.example.startracker.entities.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivityView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private String id;
    private ImagesActivityController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.id = extras.getString("key");
        }
        this.controller = new ImagesActivityController(this, id);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);
        controller.getImagesController();
    }

    public void addAdapter(List<Upload> mUploads){
        mAdapter = new ImageAdapter(ImagesActivityView.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mProgressCircle.setVisibility(View.INVISIBLE);
    }

    public void setToastView(String message) {
        Toast.makeText(ImagesActivityView.this, message, Toast.LENGTH_SHORT).show();
        mProgressCircle.setVisibility(View.INVISIBLE);
    }
}
