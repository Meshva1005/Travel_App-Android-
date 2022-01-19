package com.example.tempauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgetPassword extends AppCompatActivity {

     EditText forgetEmail;
     Button forgetButton;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetEmail = findViewById(R.id.forgetEmail);
        forgetButton = findViewById(R.id.forgetButton);

        auth = FirebaseAuth.getInstance();

        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }

    public void resetPassword(){
        String fEmail = forgetEmail.getText().toString().trim();

        if(fEmail.isEmpty()){
            forgetEmail.setError("Please enter email to get the reset link");
            forgetEmail.requestFocus();
            return;
        }
        if(!EMAIL_ADDRESS_PATTERN.matcher(fEmail).matches()){
            forgetEmail.setError("Please enter your valid email id");
            forgetEmail.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(fEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "Check your email for password reset link", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ForgetPassword.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );
}