package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    Button anonymous_btn, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
       anonymous_btn = findViewById(R.id.anonymous_btn);
       btnLogin = findViewById(R.id.login_types_btn);

        anonymous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Authenticate anonymously and start MainActivity
                FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(StartActivity.this, MainActivity.class));
                            //finish();

                        } else {
                            Utility.showToast(StartActivity.this,task.getException().getLocalizedMessage());
                        }
                        finish();
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start LoginActivity
                startActivity(new Intent(StartActivity.this, ChooseLoginActivity.class));
            }
        });
    }
}