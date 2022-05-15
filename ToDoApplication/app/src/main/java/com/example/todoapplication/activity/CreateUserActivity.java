package com.example.todoapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapplication.R;
import com.example.todoapplication.databinding.ActivityCreateUserBinding;
import com.example.todoapplication.model.User;
import com.example.todoapplication.viewmodel.UserViewModel;

import java.util.Objects;

public class CreateUserActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    Button signupBtn;
    EditText inputUsername;
    EditText inputUserEmail;
    EditText inputUserPassword;
    TextView goToLogin;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        signupBtn = findViewById(R.id.signup_btn);
        inputUsername = findViewById(R.id.username_signup);
        inputUserEmail = findViewById(R.id.email_signup);
        inputUserPassword = findViewById(R.id.password_signup);
        goToLogin = findViewById(R.id.go_to_login_text);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        goToLogin.setOnClickListener(event -> {
            finish();
        });

        signupBtn.setOnClickListener(event -> {
            String username = inputUsername.getText().toString();
            String email = inputUserEmail.getText().toString();
            String password = inputUserPassword.getText().toString();

            if (username.isEmpty()) {
                inputUsername.setError("Please, fill username field");
            }

            if (email.isEmpty()) {
                inputUserEmail.setError("Please, fill email field");
            }

            if (password.isEmpty()) {
                inputUserPassword.setError("Please, fill password field");
            }

            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                userViewModel.getAllUser.observe(this, users -> {
                    boolean isCreated = false;

                    for (User user : users) {
                        if (user.email.equals(email)) {
                            Toast.makeText(this, "User Email Already Registered", Toast.LENGTH_SHORT).show();
                            isCreated = true;
                        }
                    }

                    if (!isCreated) {
                        User user = new User();
                        user.username = username;
                        user.email = email;
                        user.password = password;
                        userViewModel.insertUser_vm(user);

                        Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                });


            }
        });
    }
}