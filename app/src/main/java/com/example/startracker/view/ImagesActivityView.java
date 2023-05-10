package com.example.startracker.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
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
    private int flag;
    private String str_flag;
    private PyObject pyobj;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ImagesActivityView.this , ActivityMenuView.class);
        intent.putExtra("key",id);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.id = extras.getString("key");
            this.flag =Integer.parseInt(extras.getString("flag")) ;
            this.str_flag = extras.getString("flag");

        }
        this.controller = new ImagesActivityController(this, id, flag);
        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        pyobj = py.getModule("script");
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);
        controller.getImagesController();
    }

    public void addAdapter(List<Upload> mUploads){
        mAdapter = new ImageAdapter(ImagesActivityView.this, mUploads, this.flag, this);
        mRecyclerView.setAdapter(mAdapter);
        mProgressCircle.setVisibility(View.INVISIBLE);
    }

    public void setToastView(String message) {
        Toast.makeText(ImagesActivityView.this, message, Toast.LENGTH_SHORT).show();
        mProgressCircle.setVisibility(View.INVISIBLE);
    }

    public void deleteItemView(String realDataId, String storageId) {
        controller.deleteItemController(realDataId, storageId);
    }

    public void passThisPageView() {
        Intent intent = new Intent(ImagesActivityView.this, ImagesActivityView.class);
        intent.putExtra("key",id);
        intent.putExtra("flag",str_flag);
        startActivity(intent);

    }

    public  void algo(String ImageUrl, String idStorage, String idData) {
        Intent intent = new Intent(ImagesActivityView.this, addImageView.class);
        intent.putExtra("key",id);
        intent.putExtra("flag","1");
        intent.putExtra("url", ImageUrl);
        intent.putExtra("idStorage",idStorage);
        intent.putExtra("idData",idData);
        startActivity(intent);
        finish();
    }
}
