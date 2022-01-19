package com.example.tempauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView mainBanner, mainDesc, mainForgetPassword, mainRegister;
    EditText mainEmail, mainPassword;
    Button mainLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mainBanner = findViewById(R.id.mainBanner);
        mainForgetPassword = findViewById(R.id.mainForgetPassword);
        mainRegister = findViewById(R.id.mainRegister);
        mainEmail = findViewById(R.id.mainEmail);
        mainPassword = findViewById(R.id.mainPassword);
        mainLogin = findViewById(R.id.mainLogin);

        mainRegister.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Register.class);
            startActivity(i);

        });
        mainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        mainForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ForgetPassword.class));
            }
        });


    }

    public void userLogin(){
        String uMainEmail = mainEmail.getText().toString().trim();
        String uMainPassword = mainPassword.getText().toString().trim();

        if(uMainEmail.isEmpty()){
            mainEmail.setError("Please enter email id to login");
            mainEmail.requestFocus();
            return;
        }
        if(!EMAIL_ADDRESS_PATTERN.matcher(uMainEmail).matches()){
            mainEmail.setError("Please enter your valid email id");
            mainEmail.requestFocus();
            return;
        }
        if(uMainPassword.isEmpty()){
            mainPassword.setError("Please enter password");
            mainPassword.requestFocus();
            return;
        }
        if(uMainPassword.length() < 6){
            mainPassword.setError("Minimum password length should be 6");
            mainPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(uMainEmail, uMainPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, product_list.class));
                }
                else{
                    Toast.makeText(MainActivity.this, "Please check your details: ", Toast.LENGTH_LONG).show();
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