package com.example.tempauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    TextView registerBanner, registerDesc;
    EditText registerName, registerEmail, registerPassword, registerConfrimPassword;
    Button registerRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registerBanner = findViewById(R.id.registerBanner);
        registerDesc = findViewById(R.id.registerDesc);
        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerMail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfrimPassword = findViewById(R.id.registerConfirmPassword);
        registerRegister = findViewById(R.id.registerRegister);



        registerBanner.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
        });

        registerRegister.setOnClickListener(view -> {
            registerUser();
        });


    }

    public void registerUser(){
        String uName = registerName.getText().toString().trim();
        String uEmail = registerEmail.getText().toString().trim();
        String uPassword = registerPassword.getText().toString().trim();
        String uConfirmPassword = registerConfrimPassword.getText().toString().trim();

        if(uName.isEmpty()){
            registerName.setError("Please enter your full name");
            registerName.requestFocus();
            return;
        }
        if(uEmail.isEmpty()){
            registerEmail.setError("Please enter  email id");
            registerEmail.requestFocus();
            return;
        }
        if(!EMAIL_ADDRESS_PATTERN.matcher(uEmail).matches()){
            registerEmail.setError("Please enter your valid email id");
            registerEmail.requestFocus();
            return;
        }
        if(uPassword.isEmpty()){
            registerPassword.setError("Please enter your password");
            registerPassword.requestFocus();
            return;
        }
        if(uPassword.length() < 6){
            registerPassword.setError("Minimum length of password should be 6 character");
            registerPassword.requestFocus();
        }
        if(uConfirmPassword.isEmpty()){
            registerConfrimPassword.setError("Please confirm your password");
            registerConfrimPassword.requestFocus();
            return;
        }
        if(!uPassword.equals(uConfirmPassword)){
            registerConfrimPassword.setError("Please confirm your password");
            registerConfrimPassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(uEmail,uPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(uName, uEmail);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Toast.makeText(Register.this, "User has been registered successfully!!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Register.this, MainActivity.class));
                                    }
                                    else {
                                        Toast.makeText(Register.this, "Something went wrong!!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {

                            Toast.makeText(Register.this, "Oopss..!!Something went wrong!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });


//        mAuth.createUserWithEmailAndPassword(uEmail, uPassword)
//                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Toast.makeText(Register.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(Register.this, "Authentication failed." + task.getException(),
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            startActivity(new Intent(Register.this, MainActivity.class));
//                            finish();
//                        }
//                    }
//                });


//        mAuth.createUserWithEmailAndPassword(uEmail, uPassword).addOnCompleteListener(this, OnCompleteListener { task ->
//            if(task.isSuccessful){
//                Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show();
//                Intent intent = Intent(this, MainActivity::class.java)
//                startActivity(intent);
//                finish();
//            }else {
//                Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show();
//            }
//        })

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