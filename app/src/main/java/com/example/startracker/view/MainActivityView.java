package com.example.startracker.view;

import android.content.Intent;
import android.os.Bundle;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.startracker.R;
import com.example.startracker.controller.MainActivityController;
import com.example.startracker.entities.User;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.startracker.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivityView extends AppCompatActivity {
    private Button sing_in;
    private Button sing_up;
    private Button forget;
    private EditText password;
    private EditText userName;
    MainActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new MainActivityController(this);
        sing_in = findViewById(R.id.button2);
        sing_up = findViewById(R.id.button3);
        forget = findViewById(R.id.button);
        password = findViewById(R.id.edtTxt2);
        userName = findViewById(R.id.editTextName);
        sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityView.this, signUpView.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityView.this, forgetPasswordView.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }

        });

        sing_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userName.getText().toString();
                String passwordE = password.getText().toString();
                controller.check_empty(username,passwordE);
            }
        });

    }

    public void noUserName(){
        userName.setError("Email cannot be empty");
        userName.requestFocus();
    }
    public void noPassword(){
        password.setError("Password cannot be empty");
        password.requestFocus();
    }
    public void  toast_view(String msg){

        Toast.makeText(MainActivityView.this,msg, Toast.LENGTH_SHORT).show();
    }
    public void paasMenuActivity(User user){
        Intent intent=new Intent(MainActivityView.this, ActivityMenuView.class);
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }


}

