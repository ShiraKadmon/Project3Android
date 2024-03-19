package com.example.project3android.Activities;

import static com.example.project3android.Image.BitMapClass.bitmapToString;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostsViewModel;
import com.example.project3android.Image.GetImageFromUser;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.SignUp.SignUpViewModel;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.User;
import com.example.project3android.User.UserViewModel;
import com.example.project3android.Validation.PasswordValidator;
import com.example.project3android.Validation.UserNameValidator;
import com.example.project3android.Validation.Validation;


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
        EditText firstName = findViewById(R.id.first_name_of_user); // get confirm password
        EditText lastName = findViewById(R.id.last_name_of_user);

        GetImageFromUser.checkReadExternalStoragePermission(this);

        UserNameValidator userNameValidator = new UserNameValidator(userName, validator);  // Username validator
        userName.addTextChangedListener(userNameValidator);

        PasswordValidator passwordValidator = new PasswordValidator(password, validator); // Password validator
        password.addTextChangedListener(passwordValidator);

        addImageBtn.setOnClickListener(v -> {
            GetImageFromUser.pickImage(this, 1);
        });

        if (getIntent().getIntExtra("edit", 0) == 1) {
            userName.setText(CurrentUser.getInstance().getCurrentUser().getUsername());
            firstName.setText(CurrentUser.getInstance().getCurrentUser().getFirstName());
            lastName.setText(CurrentUser.getInstance().getCurrentUser().getLastName());
            selectedBitmap = CurrentUser.getInstance().getCurrentUser().getBitmapProfileImage();
            ImageView profileImage = findViewById(R.id.newProfile);
            profileImage.setImageBitmap(CurrentUser.getInstance().getCurrentUser()
                    .getBitmapProfileImage());
        }

        signUpBtn.setOnClickListener(v -> {
            // check input validity before logging in
            if (checkContentDetails(userName, password, confirmPassword, firstName, lastName,
                    selectedBitmap)) {
                if (getIntent().getIntExtra("edit", 0) == 1) {
                    UserViewModel userViewModel = new ViewModelProvider(this)
                            .get(UserViewModel.class);
                    User user = new User(firstName.getText().toString(),
                            lastName.getText().toString(), userName.getText().toString(),
                            password.getText().toString(),
                            bitmapToString(selectedBitmap));
                    user.set_id(CurrentUser.getInstance().getId());
                    userViewModel.edit(user);

                    View popupView = LayoutInflater.from(MyApplication.context).
                            inflate(R.layout.signup_popup_window, null);
                    PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.
                            LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                    TextView textView = popupView.findViewById(R.id.problem_description);

                    // Update TextView content if needed
                    textView.setText("Editing Completed Successfully! \n please Login Again");
                    // Set touch listener to dismiss the popup window when tapped outside of it
                    ImageButton closeButton = popupView.findViewById(R.id.closeBtn);
                    closeButton.setOnClickListener(closeView -> {
                        PostsViewModel postsViewModel = new ViewModelProvider(this)
                                .get(PostsViewModel.class);
                        postsViewModel.get().observe(this, posts -> {
                            for (Post post : posts) {
                                if (post.getUser().get_id().equals(CurrentUser.getInstance()
                                        .getId())) {
                                    post.setAuthor_name(CurrentUser.getInstance().getCurrentUser()
                                            .getFirstName() + " " + CurrentUser.getInstance()
                                            .getCurrentUser().getLastName());
                                    post.setAuthor_image(CurrentUser.getInstance().getCurrentUser()
                                            .getProfileImage());
                                    post.setUser(CurrentUser.getInstance().getCurrentUser());
                                    postsViewModel.update(post);
                                }
                            }
                        });
                        CurrentUser.getInstance().logout();
                        popupWindow.dismiss();
                        Intent i = new Intent(MyApplication.context, MainActivity.class);
                        startActivity(i);
                    });
                }
                // if both username and password are valid - log in
                SignUpViewModel signUpViewModel = new ViewModelProvider(this)
                        .get(SignUpViewModel.class);
                User user = new User(firstName.getText().toString(),
                        lastName.getText().toString(), userName.getText().toString(),
                        password.getText().toString(), bitmapToString(selectedBitmap));
                signUpViewModel.add(user);
                signUpViewModel.get().observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                            // internet problem
                        View popupView = LayoutInflater.from(MyApplication.context).
                                inflate(R.layout.signup_popup_window, null);
                        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.
                                LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                        TextView textView = popupView.findViewById(R.id.problem_description);

                        // Update TextView content if needed
                        textView.setText(s);
                        // Set touch listener to dismiss the popup window when tapped outside of it
                        ImageButton closeButton = popupView.findViewById(R.id.closeBtn);
                        closeButton.setOnClickListener(closeView -> {
                            popupWindow.dismiss();
                            if (s.equals("Welcome! \n please Login")) {
                                Intent i = new Intent(MyApplication.context, MainActivity.class);
                                startActivity(i);
                            }
                        });
                    }
                });
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
            selectedBitmap = GetImageFromUser.handleImageResult(this,
                    data, cornerRadius);
            if (selectedBitmap != null) {
                showProfilePic.setImageBitmap(selectedBitmap);
            } else {
                Toast.makeText(this, "Please add an image",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkContentDetails(EditText userName, EditText password, EditText confirmPass,
                                        EditText firstName, EditText lastName, Bitmap image) {
        String inputUserName = userName.getText().toString(); // get the username and the password
        String inputPassword = password.getText().toString();
        String inputConfirmPassword = confirmPass.getText().toString();
        String inputFirstName = firstName.getText().toString();
        String inputLastName = lastName.getText().toString();
        return ((validator.isValidUN(inputUserName)) &&
                (validator.isValidPass(inputPassword)) &&
                inputPassword.equals(inputConfirmPassword) &&
                !inputFirstName.equals("") &&
                !inputLastName.equals("") &&
                image != null &&
                !image.isRecycled() &&
                image.getWidth() > 0 &&
                image.getHeight() > 0);
    }
}
