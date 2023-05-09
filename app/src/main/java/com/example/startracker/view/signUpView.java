package com.example.startracker.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.startracker.R;
import com.example.startracker.controller.signUpController;
import com.google.firebase.auth.FirebaseUser;

public class signUpView extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText password;
    private Button register;
    private Button loginUser;
    private signUpController controller;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(signUpView.this , MainActivityView.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        controller = new signUpController(this);
        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.edtTxt2);
        register = findViewById(R.id.button3);
        loginUser = findViewById(R.id.button4);
        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signUpView.this , MainActivityView.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName = name.getText().toString();
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();
                System.out.println("name: "+txtName+" email: "+txtEmail+" password: "+txtPassword);
                controller.registerUserController(txtName , txtEmail , txtPassword);
            }
        });

    }
    public void setToastView(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public void passPage(){
        Intent intent = new Intent(signUpView.this , MainActivityView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}

