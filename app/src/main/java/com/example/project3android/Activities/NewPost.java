package com.example.project3android.Activities;

import static com.example.project3android.Image.BitMapClass.bitmapToString;
import static com.example.project3android.Image.BitMapClass.getImageUri;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostsViewModel;
import com.example.project3android.Image.BitMapClass;
import com.example.project3android.Image.GetImageFromUser;
import com.example.project3android.R;
import com.example.project3android.User.CurrentUser;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.http.HEAD;


public class NewPost extends AppCompatActivity {
    private PostsViewModel postsViewModel;
    private ImageView addImageBtn;
    private Button shareBtn;
    private Bitmap selectedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);

        String userName = CurrentUser.getInstance().getCurrentUser().getFirstName() + " " +
                CurrentUser.getInstance().getCurrentUser().getLastName();
        Bitmap profileImage = CurrentUser.getInstance().getCurrentUser().getBitmapProfileImage();

        // set the username
        TextView tvUsername = findViewById(R.id.username);
        tvUsername.setText(userName);
        ImageView ivProfileImage = findViewById(R.id.image_profile_new_post);
        ivProfileImage.setImageBitmap(profileImage);

        addImageBtn = findViewById(R.id.add_image_new_post); // Add profile image button
        shareBtn = findViewById(R.id.share_btn); // share button
        ImageView closeBtn = findViewById(R.id.close_tag_new_post); // share button

        EditText postText = findViewById(R.id.text_input_editText);
        // if this is edit post, set the current data
        if (getIntent().getSerializableExtra("post") != null) {
           Post post = (Post) getIntent().getSerializableExtra("post");
            postText.setText(post.getText());
            ImageView userImage = findViewById(R.id.user_post_image);
            userImage.setImageBitmap(post.getBitmapPic());
            selectedBitmap = post.getBitmapPic();
        }

        GetImageFromUser.checkReadExternalStoragePermission(this);

        addImageBtn.setOnClickListener(v -> {
            GetImageFromUser.pickImage(this, 1);
        });

        shareBtn.setOnClickListener(v -> {
            // check input validity before logging in
            if (checkContentDetails(postText, selectedBitmap)) {
                // if both username and password are valid - log in
                String pic = getImageUri(this, selectedBitmap).getPath();
                String profilePic = getImageUri(this, profileImage).getPath();
                //String selectedBase64 = bitmapToString(selectedBitmap);
                //String profileBase64 = bitmapToString(profileImage);

                Post newPost = new Post(CurrentUser.getInstance().getCurrentUser(),
                        userName, postText.getText().toString(),
                        pic, DateFormat.getDateInstance().format(new Date()),
                        profilePic, new ArrayList<>());

                if (getIntent().getSerializableExtra("post") != null) {
                    Post post = (Post) getIntent().getSerializableExtra("post");
                    newPost.setPostId(post.getPostId());
                    newPost.setAuthor_image(post.getAuthor_image());
                    postsViewModel.update(newPost);
                } else {
                    postsViewModel.add(newPost);
                }
                finish();
            } else if (selectedBitmap == null) {
                //  error message to the user
                Toast.makeText(this, "Please add an image", Toast.LENGTH_SHORT).show();
            }
        });

        closeBtn.setOnClickListener(v-> finish());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GetImageFromUser.onRequestPermissionsResult(this, requestCode,
                permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            int cornerRadius = 0; // Adjust the corner radius
            ImageView showProfilePic = findViewById(R.id.user_post_image);
            selectedBitmap = GetImageFromUser.handleImageResult(this, data, cornerRadius);
            if (selectedBitmap != null) {
                // Get the screen width
                DisplayMetrics displayMetrics = new DisplayMetrics();
                this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                // Get LinearLayout height
                LinearLayout linearLayout = findViewById(R.id.post_image_content);
                float height = linearLayout.getHeight() *  (2.0f / 3.0f);

                selectedBitmap = BitMapClass.resizeBitmap(selectedBitmap, screenWidth, height);
                showProfilePic.setImageBitmap(selectedBitmap);
            } else {
                Toast.makeText(this, "Please add an image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkContentDetails(EditText text, Bitmap image) {
        String inputText = text.getText().toString();
        return (!inputText.equals("") &&
                image != null &&
                !image.isRecycled() &&
                image.getWidth() > 0 &&
                image.getHeight() > 0);
    }

}

