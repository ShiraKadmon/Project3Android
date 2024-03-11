package com.example.project3android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project3android.Feed.Feed;
import com.example.project3android.Feed.FeedData;
import com.example.project3android.UserNameValidator;
import com.example.project3android.Validation;


public class SignUp extends AppCompatActivity {
    private LinearLayout addImageBtn;
    private Bitmap selectedBitmap;
    private Button signUpBtn;
    private Button logInBtn;
    Validation validator = new Validation();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        addImageBtn = findViewById(R.id.add_image); // Add profile image button
        signUpBtn = findViewById(R.id.signUp_details_btn); // SignUp button
        logInBtn = findViewById(R.id.login_in_signUp_btn); // Back to login button

        EditText userName = findViewById(R.id.login_etUsername); // get the entered username
        EditText password = findViewById(R.id.login_etPassword); // get the entered password
        EditText confirmPassword = findViewById(R.id.confirm_password); // get confirm password
        EditText name = findViewById(R.id.name_of_user); // get confirm password

        GetImageFromUser.checkReadExternalStoragePermission(this);

        UserNameValidator userNameValidator = new UserNameValidator(userName, validator);  // Username validator
        userName.addTextChangedListener(userNameValidator);

        PasswordValidator passwordValidator = new PasswordValidator(password, validator); // Password validator
        password.addTextChangedListener(passwordValidator);

        addImageBtn.setOnClickListener(v -> {
            GetImageFromUser.pickImage(this, 1);
        });

        signUpBtn.setOnClickListener(v -> {
            // check input validity before logging in
            if (checkContentDetails(userName, password, confirmPassword, name, selectedBitmap)) {
                // if both username and password are valid - log in
                GetImageFromUser.saveImageToGallery(this, selectedBitmap);
                FeedData.getInstance().setUserName(userName.getText().toString());
                FeedData.getInstance().setProfileImage(selectedBitmap);
                Intent i = new Intent(this, Feed.class);
                startActivity(i);
            } else if (selectedBitmap == null) {
                //  error message to the user
                Toast.makeText(this, "Please add an image", Toast.LENGTH_SHORT).show();
            }
        });


        // use login button to go to login page
        logInBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GetImageFromUser.onRequestPermissionsResult(this, requestCode, permissions,
                grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            int cornerRadius = 50; // Adjust the corner radius
            ImageView showProfilePic = findViewById(R.id.newProfile);
            selectedBitmap = GetImageFromUser.handleImageResult(this, data, cornerRadius);
            if (selectedBitmap != null) {
                showProfilePic.setImageBitmap(selectedBitmap);
            } else {
                Toast.makeText(this, "Please add an image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkContentDetails(EditText userName, EditText password, EditText confirmPass,
                                        EditText name, Bitmap image) {
        String inputUserName = userName.getText().toString(); // get the username and the password
        String inputPassword = password.getText().toString();
        String inputConfirmPassword = confirmPass.getText().toString();
        String inputName = name.getText().toString();
        return ((validator.isValidUN(inputUserName)) &&
                (validator.isValidPass(inputPassword)) &&
                inputPassword.equals(inputConfirmPassword) &&
                !inputName.equals("") &&
                image != null &&
                !image.isRecycled() &&
                image.getWidth() > 0 &&
                image.getHeight() > 0);
    }
}
