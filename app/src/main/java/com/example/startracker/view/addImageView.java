package com.example.startracker.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.startracker.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import com.example.startracker.controller.addImageController;
import com.example.startracker.entities.ImageRequest;
import com.example.startracker.entities.ImageResponse;
import com.example.startracker.entities.serverAPI;
import com.example.startracker.entities.star;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class addImageView extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION_CODE = 2;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private String id;
    private Bitmap imageBitmap;
    private addImageController controller;
    private PyObject pyobj;
    private String refId;
    private String refProcessedId;
    private String storageId;
    private String storageProcessedId;
    private String coordinates;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(addImageView.this , ActivityMenuView.class);
        intent.putExtra("key",id);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        mImageView = findViewById(R.id.image_view);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.id = extras.getString("key");
        }
        this.controller = new addImageController(this,id);

        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        pyobj = py.getModule("script");
        this.coordinates = "";
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_algo);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mProgressBar = findViewById(R.id.progress_bar);
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(addImageView.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(addImageView.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
                        return;
                    }
                }
                dispatchTakePictureIntent();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (TextUtils.isEmpty(mEditTextFileName.getText().toString())) {
                        mEditTextFileName.setError("name cannot be empty");
                        mEditTextFileName.requestFocus();
                    }
                    else{
                            controller.uploadFileController(imageBitmap,mEditTextFileName.getText().toString());
                    }
            }
        });

    }

    public void setToastView(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void setProgressView(int num){
        mProgressBar.setProgress(num);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            this.imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "The permission to write to storage was denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveImageToStorage(Bitmap imageBitmap) {
        if(imageBitmap!=null){
            String fileName = "stars.jpg";
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File file = new File(getExternalFilesDir(null), fileName);
            try {
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.close();
                draw_stars();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "There was an error while saving the image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void useAlgorithm(String url) {
        ImageRequest imageRequest = new ImageRequest(url);
        Call<ImageResponse> ImageResponseCall = serverAPI.getService().algorithm(imageRequest);
        ImageResponseCall.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(@NonNull Call<ImageResponse> call, @NonNull Response<ImageResponse> response) {
                if(response.isSuccessful()){
                    ImageResponse res = response.body();
                    assert res != null;
                    coordinates = res.getStars_id();
                    saveImageToStorage(imageBitmap);
                    System.out.println(res.getStars_id());
                }else{
                    controller.toast_controller("response error");
                }
            }
            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                controller.toast_controller("error");
            }
        });
    }

    public void getProcessedId(String ImageUrl,String[] names){
        this.setToastView("Algorithm successful");
        this.refProcessedId = names[1];
        this.storageProcessedId = names[0];
        addStars(ImageUrl);
    }

    public void pass_to_newImage(String ImageUrl){
        Intent intent = new Intent(addImageView.this , newImageView.class);
        intent.putExtra("key",id);
        intent.putExtra("url",ImageUrl);
        intent.putExtra("refId",this.refId);
        intent.putExtra("storageId",this.storageId);
        intent.putExtra("refProcessedId",this.refProcessedId);
        intent.putExtra("storageProcessedId",this.storageProcessedId);
        startActivity(intent);
        finish();
    }

    public  void draw_stars() {
        String deviceFilePath = "/storage/emulated/0/Android/data/com.example.startracker/files/stars.jpg";
        System.out.println("coordinates: "+coordinates);
        PyObject obj =  pyobj.callAttr("draw_image",deviceFilePath, this.coordinates);
        String path = obj.toString();
        System.out.println("path= "+path);
        controller.uploadNewImageController(path, mEditTextFileName.getText().toString());
    }

    public void addStars(String imageUrl){
        List<star> stars = new ArrayList<>();
        String[] st_split = this.coordinates.split("\\[")[1].split("\\]")[0].split(",");
        int count = 0;
        int id_star = 0;
        String name = "";
        float x = 0.0f;
        float y = 0.0f;

        for (String s : st_split) {
            if (s.contains("(")) {
                count = 1;
                id_star = Integer.parseInt(s.split("\\(")[1]);
            } else if (s.contains(")")) {
                count = 0;
                float r = Float.parseFloat(s.split("\\)")[0]);
                star new_star = new star(id_star+"", name, x+"", y+"", r+"");
                stars.add(new_star);
            } else {
                count += 1;
                if (count == 2) {
                    name = s;
                } else if (count == 3) {
                    x = Float.parseFloat(s);
                } else if (count == 4) {
                    y = Float.parseFloat(s);
                }
            }
        }
        controller.addStarsController(this.refProcessedId, stars, imageUrl);
    }

    public void getIdView(String[] names, String url){
        this.refId = names[1];
        this.storageId = names[0];
        useAlgorithm(url);
    }
}





