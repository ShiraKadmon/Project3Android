package com.example.project3android.Feed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3android.Feed.Comments;
import com.example.project3android.Feed.FeedData;
import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.adapters.PostListAdapter;
import com.example.project3android.Feed.data.PostConverter;
import com.example.project3android.NewPost;
import com.example.project3android.R;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class Feed extends AppCompatActivity {
    private Activity currentActivity;
    private PostListAdapter adapter;
    private boolean isNightMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        currentActivity = this;

        if (FeedData.getInstance().getProfileImage() == null) {
            FeedData.getInstance().setProfileImage(BitmapFactory.decodeResource(
                                                            getResources(), R.drawable.profile));
        }
        Bitmap profileImage = FeedData.getInstance().getProfileImage();

        ImageView ivProfileImage = findViewById(R.id.profileImageFeed);
        ivProfileImage.setImageBitmap(profileImage);

        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        adapter = new PostListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        FeedData.getInstance().setAdapter(adapter);

        // Read the JSON file from the raw directory
        InputStream inputStream = getResources().openRawResource(R.raw.posts);

        // Convert InputStream to String
        String jsonString = convertStreamToString(inputStream);
        PostConverter postConverter = new PostConverter(jsonString);
        List<Post> posts = postConverter.convertJsonToPostList();
        FeedData.getInstance().setPosts(posts);
        adapter.setPosts(FeedData.getInstance().getPosts());

        Button logoutButton = findViewById(R.id.logoutBtn);
        logoutButton.setOnClickListener(v -> {
            finish();
        });

        ImageButton addPost = findViewById(R.id.addPostBtn);
        addPost.setOnClickListener(v -> {
            Intent i = new Intent(this, NewPost.class);
            startActivity(i);
            adapter.notifyDataSetChanged();
        });

        EditText newPost = findViewById(R.id.addPostText);
        newPost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Intent i = new Intent(currentActivity, NewPost.class);
                startActivity(i);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch theme = findViewById(R.id.theme);
        theme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Dark theme is enabled
                findViewById(R.id.topLayoutFeed).setBackgroundColor(Color.BLACK);
                findViewById(R.id.secondLayoutFeed).setBackgroundColor(Color.BLACK);
                findViewById(R.id.bottomLayoutFeed).setBackgroundColor(Color.BLACK);
                adapter.setNightMode(true);
                this.isNightMode = true;

            } else {
                // Light theme is enabled
                findViewById(R.id.topLayoutFeed).setBackgroundColor(Color.WHITE);
                findViewById(R.id.secondLayoutFeed).setBackgroundColor(Color.WHITE);
                findViewById(R.id.bottomLayoutFeed).setBackgroundColor(Color.WHITE);
                adapter.setNightMode(false);
                this.isNightMode = false;
            }
        });
    }

    private String convertStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    public void editPost(Post post, int position) {
        Intent i = new Intent(this, NewPost.class);
        i.putExtra("position", position);
        startActivity(i);
    }

    public void addComment(Post post, int position) {
        Intent i = new Intent(this, Comments.class);
        i.putExtra("position", position);
        i.putExtra("nightMode", isNightMode);
        startActivity(i);
    }
}

