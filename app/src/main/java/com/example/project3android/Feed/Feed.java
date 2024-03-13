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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.project3android.API.PostAPI;
import com.example.project3android.Feed.Comments;
import com.example.project3android.Feed.FeedData;
import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.ViewModels.PostsViewModel;
import com.example.project3android.Feed.adapters.PostListAdapter;
import com.example.project3android.Feed.data.PostConverter;
import com.example.project3android.NewPost;
import com.example.project3android.R;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.UserViewModel;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class Feed extends AppCompatActivity {
    private Activity currentActivity;
    private PostListAdapter adapter;
    private boolean isNightMode = false;
    private PostsViewModel postViewModel;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        currentActivity = this;

        postViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.add(CurrentUser.getInstance().getCurrentUser());
        userViewModel.getJWT(CurrentUser.getInstance().getCurrentUser());

        ImageView ivProfileImage = findViewById(R.id.profileImageFeed);
        ivProfileImage.setImageBitmap(CurrentUser.getInstance().getCurrentUser().getBitmapProfileImage());

        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        adapter = new PostListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        postViewModel.get().observe(this, posts -> adapter.setPosts(posts));

        //logout
        Button logoutButton = findViewById(R.id.logoutBtn);
        logoutButton.setOnClickListener(v -> {
            finish();
        });

        //add post
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

        // theme
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

        // refresh
        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> {
            postViewModel.reload();
        });
    }

    public void editPost(int id) {
        Intent i = new Intent(this, NewPost.class);
        i.putExtra("position", id);
        startActivity(i);
    }

    public void addComment(int id) {
        Intent i = new Intent(this, Comments.class);
        i.putExtra("position", id);
        i.putExtra("nightMode", isNightMode);
        startActivity(i);
    }

    public void deletePost(Post post) {
        postViewModel.delete(post);
    }
}

