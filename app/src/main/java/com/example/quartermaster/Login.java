package com.example.quartermaster;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mToRegister;//forgotTextLink;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mLoginBtn = findViewById(R.id.LoginBtn);
        mToRegister = findViewById(R.id.toRegister);


        mLoginBtn.setOnClickListener(v -> {

            String loginemail = mEmail.getText().toString().trim();
            String loginpassword = mPassword.getText().toString().trim();

            if(TextUtils.isEmpty(loginemail)){
                mEmail.setError("Email is Required.");
                return;
            }

            if(TextUtils.isEmpty(loginpassword)){
                mPassword.setError("Password is Required.");
                return;
            }

            if(loginpassword.length() < 6){
                mPassword.setError("Password Must be >= 6 Characters");
                return;
            }


            // authenticate the user

            fAuth.signInWithEmailAndPassword(loginemail,loginpassword).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    mPassword.setText("");
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else {
                    Toast.makeText(Login.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }

            });

        });

        mToRegister.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Register.class)));

//        forgotTextLink.setOnClickListener(v -> {
//
//            final EditText resetMail = new EditText(v.getContext());
//            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
//            passwordResetDialog.setTitle("Reset Password ?");
//            passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
//            passwordResetDialog.setView(resetMail);
//
//            passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
//                // extract the email and send reset link
//                String mail = resetMail.getText().toString();
//                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid -> Toast.makeText(Login.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(Login.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show());
//
//            });
//
//            passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
//                // close the dialog
//            });
//
//            passwordResetDialog.create().show();
//
//        });


    }
}