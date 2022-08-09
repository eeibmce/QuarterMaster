package com.example.quartermaster;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        private FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                reload();
            }
        }

        private EditText email, password;
        private Button registerBtn;
        //private TextView loadingIcon;

        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerBtn);
        //loadingIcon = findViewById(R.id.LoadingIcon);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }



}