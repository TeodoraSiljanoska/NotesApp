package com.example.notesapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleLogin extends AppCompatActivity {
        private FirebaseAuth mAuth;
        private static final int RC_SIGN_IN = 123;
        private GoogleSignInClient mGoogleSignInClient;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_google_login);
            mAuth = FirebaseAuth.getInstance();

            // Configure Google Sign-in options
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("747879298205-m9jo5vcfn17s3r0qbukcbgmn71gbgidp.apps.googleusercontent.com")
                    /*.requestIdToken(getString(R.string.default_web_client_id))*/
                    .requestEmail()
                    .setHostedDomain("gmail.com")
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

            // Set up Google Sign-in button
            Button btnLogin = findViewById(R.id.google_sign_in_button);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });
        }

        private void signIn() {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign-in was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    // Google Sign-in failed
                    Log.e(TAG, "Google sign-in failed.", e);
                }
            }
        }

        private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Authentication successful, update UI with user information
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // Authentication failed, display error message
                                String message = getResources().getString(R.string.auth_failed);
                                Utility.showToast(GoogleLogin.this, message);
                            }
                        }
                    });
        }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in
            String displayName = user.getDisplayName();
            // Update UI with the user's name and profile picture
            String message = getResources().getString(R.string.welcome1);
            Utility.showToast(GoogleLogin.this, message);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // User is signed out
            String message = getResources().getString(R.string.signed_out);
            Utility.showToast(GoogleLogin.this, message);        }
    }
}