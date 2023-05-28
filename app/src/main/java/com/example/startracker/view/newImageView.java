package com.example.startracker.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.startracker.R;
import com.example.startracker.controller.newImageController;
import com.squareup.picasso.Picasso;

public class newImageView extends AppCompatActivity {
    private String id;
    private String ImageUrl;
    private Button mButtonSave;
    private Button mButtonDelete;
    private Button mButtonTryAgain;
    private ImageView mImageView;
    private newImageController controller;
    private String refId;
    private String refProcessedId;
    private String storageId;
    private String storageProcessedId;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(newImageView.this, addImageView.class);
        intent.putExtra("key", id);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_new_image);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.id = extras.getString("key");
            this.ImageUrl = extras.getString("url");
            this.refId = extras.getString("refId");
            this.storageId = extras.getString("storageId");
            this.refProcessedId = extras.getString("refProcessedId");
            this.storageProcessedId = extras.getString("storageProcessedId");
        }
        mImageView = findViewById(R.id.image_view);
        Picasso.get().load(ImageUrl).into(mImageView);
        mButtonDelete = findViewById(R.id.button_delete);
        mButtonSave = findViewById(R.id.button_save);
        mButtonTryAgain = findViewById(R.id.button_try_again);
        this.controller = new newImageController(this, id);
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.deleteImageController(refId,refProcessedId,storageId,storageProcessedId,0);
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(newImageView.this, ActivityMenuView.class);
                intent.putExtra("key", id);
                finish();
            }
        });
        mButtonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(newImageView.this);
                builder.setMessage("Choose your option ?");
                builder.setCancelable(false);
                builder.setPositiveButton("delete and try again", (DialogInterface.OnClickListener) (dialog, which) -> {
                    controller.deleteImageController(refId,refProcessedId,storageId,storageProcessedId,1);
                });
                builder.setNegativeButton("save and try again", (DialogInterface.OnClickListener) (dialog, which) -> {
                    Intent intent = new Intent(newImageView.this, addImageView.class);
                    intent.putExtra("key", id);
                    startActivity(intent);
                    finish();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                positiveButton.setAllCaps(false);
                negativeButton.setAllCaps(false);
            }
            });
    }

    public void passPageMenu(){
        Intent intent = new Intent(newImageView.this, ActivityMenuView.class);
        intent.putExtra("key", id);
        startActivity(intent);
        finish();
    }

    public void passPageAdd(){
        Intent intent = new Intent(newImageView.this, addImageView.class);
        intent.putExtra("key", id);
        startActivity(intent);
        finish();
    }

}
