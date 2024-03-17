package com.example.project3android.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.project3android.Login.LoginViewModel;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.SignUp.SignUp;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.User;
import com.example.project3android.Validation.PasswordValidator;
import com.example.project3android.Validation.UserNameValidator;
import com.example.project3android.Validation.Validation;

public class MainActivity extends AppCompatActivity {
    Validation validator = new Validation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

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
                CurrentUser.getInstance().setCurrentUser(new User(inputUserName, inputPassword));
                viewModel.getJWT(CurrentUser.getInstance().getCurrentUser());
                viewModel.get().observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if (s.equals("succeeded")) {
                            // if both username and password are valid - log in
                            Intent i = new Intent(MyApplication.context, Feed.class);
                            startActivity(i);
                        }
                        else {
                            View popupView = LayoutInflater.from(MyApplication.context).inflate(
                                    R.layout.signup_popup_window, null);
                            PopupWindow popupWindow = new PopupWindow(popupView,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                            popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                            TextView textView = popupView.findViewById(R.id.problem_description);

                            // Update TextView content if needed
                            textView.setText(s);
                            // Set touch listener to dismiss the popup window when tapped outside of it
                            ImageButton closeButton = popupView.findViewById(R.id.closeBtn);
                            closeButton.setOnClickListener(closeView -> popupWindow.dismiss());
                        }
                    }
                });
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