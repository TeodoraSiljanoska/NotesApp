package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class ChooseLoginActivity extends AppCompatActivity {
Button email_and_pass_btn, gmail_btn, fb_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);
        email_and_pass_btn = findViewById(R.id.emailandpass_btn);
        gmail_btn = findViewById(R.id.gmail_btn);
        fb_btn = findViewById(R.id.fb_btn);

        email_and_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_one = new Intent(ChooseLoginActivity.this, LoginActivity.class);
                startActivity(intent_one);
            }
        });

        gmail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_two = new Intent(ChooseLoginActivity.this,GoogleLogin.class);
                startActivity(intent_two);
            }
        });

        fb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_three = new Intent(ChooseLoginActivity.this,GoogleLogin.class);
                startActivity(intent_three);
            }
        });

    }
}