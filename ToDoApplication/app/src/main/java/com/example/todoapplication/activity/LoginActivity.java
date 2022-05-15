package com.example.todoapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapplication.R;
import com.example.todoapplication.model.User;
import com.example.todoapplication.viewmodel.UserViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginBtn;
    UserViewModel userViewModel;
    TextView goToSignUpText;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        loginBtn = findViewById(R.id.login_btn);
        goToSignUpText = findViewById(R.id.go_to_signup_text);

        goToSignUpText.setOnClickListener(event -> {
            startActivity(new Intent(LoginActivity.this, CreateUserActivity.class));
        });

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //For debugging purposes
        userViewModel.getAllUser.observe(this, users -> users.forEach(singleUser -> {
            Log.i("Users : ", "username : " + singleUser.username + " || email : " + singleUser.email + " || password : " + singleUser.password);
        }));

        loginBtn.setOnClickListener((event) -> {

            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();

            if (userEmail.trim().isEmpty()) {
                email.setError("Empty Email field !");
            }

            if (userPassword.trim().isEmpty()) {
                password.setError("Empty Password field !");
            }

            if (!userEmail.isEmpty() && !userPassword.isEmpty()) {
                userViewModel.getAllUser.observe(this, users -> {
                    boolean isLoggedIn = false;

                    for (User singleUser : users) {
                        if (singleUser.email.trim().equalsIgnoreCase(userEmail.trim()) && singleUser.password.trim().equals(userPassword.trim())) {
                            Log.i("Users : ", "username : " + singleUser.username + " || email : " + singleUser.email + " || password : " + singleUser.password);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("username", singleUser.username);
                            Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            isLoggedIn = true;
                            startActivity(intent);
                            finish();
                        }
                    }

                    if (!isLoggedIn) {
                        Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}