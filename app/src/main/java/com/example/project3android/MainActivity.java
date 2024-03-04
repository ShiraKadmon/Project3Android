package com.example.project3android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project3android.Feed.Feed;
import com.example.project3android.Feed.FeedData;
import com.example.project3android.PasswordValidator;
import com.example.project3android.SignUp;
import com.example.project3android.UserNameValidator;
import com.example.project3android.Validation;

public class MainActivity extends AppCompatActivity {
    Validation validator = new Validation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText userName = findViewById(R.id.login_etUsername); // get the entered username
        EditText password = findViewById(R.id.login_etPassword); // get the entered password


        UserNameValidator userNameValidator = new UserNameValidator(userName, validator);  // Username validator
        userName.addTextChangedListener(userNameValidator);

        PasswordValidator passwordValidator = new PasswordValidator(password, validator); // Password validator
        password.addTextChangedListener(passwordValidator);


        // use Log In button to go to Feed page
        Button login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(v -> {
            // get the username and the password
            String inputUserName = userName.getText().toString();
            String inputPassword = password.getText().toString();
            // check input validity before logging in
            if ((validator.isValidUN(inputUserName)) && (validator.isValidPass(inputPassword))) {
                // if both username and password are valid - log in
                FeedData.getInstance().setUserName(inputUserName);
                Intent i = new Intent(this, Feed.class);
                startActivity(i);
            }
        });
        // use Sign Up button to go to Sign Up page
        Button signup_btn = findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        });
    }
}