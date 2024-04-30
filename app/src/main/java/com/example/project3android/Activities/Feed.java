package com.example.project3android.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostsViewModel;
import com.example.project3android.Feed.adapters.PostListAdapter;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.User;
import com.example.project3android.User.UserViewModel;

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

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        postViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        ImageView ivProfileImage = findViewById(R.id.profileImageFeed);

        userViewModel.getUser().observe(this, userResponse -> {
            if (userResponse != null) {
                CurrentUser.getInstance().setCurrentUser(userResponse.getUser());
                CurrentUser.getInstance().setFriendshipStatus(userResponse.getFriendshipStatus());
                ivProfileImage.setImageBitmap(CurrentUser.getInstance()
                        .getCurrentUser().getBitmapProfileImage());
            }
        });

        ivProfileImage.setOnClickListener(v -> {
            View popupView = LayoutInflater.from(MyApplication.context).inflate(
                    R.layout.edit_user_popup_window, null);
            PopupWindow popupWindow = new PopupWindow(popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
            Button deleteBtn = popupView.findViewById(R.id.delete);
            userViewModel.getDeleteMess().observe(this, message ->{
                    if(message == true)   {
                        CurrentUser.getInstance().logout();
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                    }
            });
            deleteBtn.setOnClickListener(deleteView -> {

                for (Post post : adapter.getPosts()) {
                    if (CurrentUser.getInstance().getId().equals(post.getUser().get_id())) {
                        postViewModel.delete(post);
                    }
                }
                userViewModel.delete();
            });
            Button editBtn = popupView.findViewById(R.id.edit);
            editBtn.setOnClickListener(editView -> {
                Intent i = new Intent(this, SignUp.class);
                i.putExtra("edit", 1);
                startActivity(i);
            });
            // Set touch listener to dismiss the popup window when tapped outside of it
            ImageButton closeButton = popupView.findViewById(R.id.closeBtn);
            closeButton.setOnClickListener(closeView -> popupWindow.dismiss());
        });

        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        adapter = new PostListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        postViewModel.getAll().observe(this, posts -> {
            adapter.setPosts(posts);
        });

        //logout
        Button logoutButton = findViewById(R.id.logoutBtn);
        logoutButton.setOnClickListener(v -> {
            CurrentUser.getInstance().logout();
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
            refreshLayout.setRefreshing(false);
        });
    }

    public void editPost(Post post) {
        Intent i = new Intent(this, NewPost.class);
        i.putExtra("post", post);
        startActivity(i);
    }

    public void addComment(Post post) {
        Intent i = new Intent(this, Comments.class);
        i.putExtra("post", post);
        i.putExtra("nightMode", isNightMode);
        startActivity(i);
    }

    public void deletePost(Post post) {
        postViewModel.delete(post);
    }

    public void profilePage(User user) {
        Intent i = new Intent(this, ProfilePage.class);
        i.putExtra("user", user);
        startActivity(i);
    }
}