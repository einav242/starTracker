package com.example.startracker.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.startracker.R;
import com.example.startracker.controller.searchStarsController;
import com.example.startracker.entities.star;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class searchStarsView extends AppCompatActivity {
    searchStarsController controller;
    private SearchView searchID;
    private String refId;
    private String refProcessedId;
    private String storageId;
    private String storageProcessedId;
    private String id;
    private String ImageUrl;
    private String coordinates;
    private ImageView mImageView;
    private String id_star;
    private Button show_all;
    private Button show_allID;
    private PyObject pyobj;
    private int flag;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent;
        if(flag == 0){
            intent = new Intent(searchStarsView.this, ActivityMenuView.class);
            intent.putExtra("key",id);
        }
        else{
            intent = new Intent(searchStarsView.this, ImagesActivityView.class);
            intent.putExtra("key",id);
        }
        startActivity(intent);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_start_activity);
        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        pyobj = py.getModule("script");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.id = extras.getString("key");
            this.flag = extras.getInt("flag");
            if(flag == 0){
                this.ImageUrl = extras.getString("url");
                this.refId = extras.getString("refId");
                this.storageId = extras.getString("storageId");
                this.refProcessedId = extras.getString("refProcessedId");
                this.storageProcessedId = extras.getString("storageProcessedId");
            }
            else{
                this.ImageUrl = extras.getString("url");
                this.refProcessedId = extras.getString("idData");
                this.storageProcessedId = extras.getString("idStorage");
                this.storageId = this.storageProcessedId;
            }

        }
        this.controller = new searchStarsController(this,id,refProcessedId);
        this.searchID = findViewById(R.id.searchStar);
        this.mImageView = findViewById(R.id.image_view);
        Picasso.get().load(ImageUrl).into(mImageView);
        show_all = findViewById(R.id.button);
        show_allID = findViewById(R.id.button5);
        controller.getCoordinatesController();
        searchID.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                controller.searchController(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        this.show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deviceFilePath = "/storage/emulated/0/Android/data/com.example.startracker/files/stars.jpg";
                PyObject obj =  pyobj.callAttr("show_all",deviceFilePath, coordinates);
                String path = obj.toString();
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                mImageView.setImageBitmap(bitmap);
            }
        });

        this.show_allID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.get().load(ImageUrl).into(mImageView);
            }
        });
    }

    public void setStarView(star s) {
        this.id_star = s.getId()+","+s.getName()+","+s.getX()+","+s.getY()+","+s.getR();
        this.controller.saveImageController(this.storageId);
    }

    public void setToastView(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void setCoordinatesView(String coordinates) {
        this.coordinates = coordinates;
    }

    public void callFunctionView() {
        String deviceFilePath = "/storage/emulated/0/Android/data/com.example.startracker/files/stars.jpg";
        PyObject obj =  pyobj.callAttr("draw_by_id",deviceFilePath, this.id_star);
        String path = obj.toString();
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        mImageView.setImageBitmap(bitmap);
        System.out.println("path= "+path);
    }
}
