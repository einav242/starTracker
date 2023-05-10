package com.example.startracker.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.startracker.R;
import com.example.startracker.controller.ActivityMenuController;
import com.example.startracker.controller.MainActivityController;
import com.example.startracker.entities.User;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.startracker.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityMenuView extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;
    private ImageButton add;
    private ImageButton gallery;
    private ImageButton processed;
    private ImageButton discovered;
    private Button logout;
    private TextView title;
    private String userName;
    private String id;
    private Uri uri;
    ActivityMenuController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("key");
        }
        this.controller = new ActivityMenuController(this, id);
        this.add = findViewById(R.id.imageButton2);
        this.gallery = findViewById(R.id.imageButton7);
        this.processed = findViewById(R.id.imageButton8);
        this.discovered = findViewById(R.id.imageButton10);
        this.logout = findViewById(R.id.singUp_LogOut);
        this.title = findViewById(R.id.txtMessage);
        controller.getUserNameController();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.logOut_controller();
                Intent intent = new Intent(ActivityMenuView.this, MainActivityView.class);
                startActivity(intent);
                finish();
                Toast.makeText(ActivityMenuView.this , "Logout Successful",Toast.LENGTH_SHORT).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMenuView.this, addImageView.class);
                intent.putExtra("key",id);
                intent.putExtra("flag","0");
                intent.putExtra("url", "0");
                intent.putExtra("idStorage","0");
                intent.putExtra("idData","0");
                startActivity(intent);
                finish();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMenuView.this, ImagesActivityView.class);
                intent.putExtra("key",id);
                intent.putExtra("flag","0");
                startActivity(intent);
            }
        });

        processed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMenuView.this, ImagesActivityView.class);
                intent.putExtra("key",id);
                intent.putExtra("flag","1");
                startActivity(intent);
            }
        });
    }




    public void setUserName(String name)
    {
        this.userName = name;
        title.setText("Hello "+this.userName);
    }

    public void setToastView(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
