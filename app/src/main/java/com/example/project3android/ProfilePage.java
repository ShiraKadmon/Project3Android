package com.example.project3android;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.project3android.User.Friends.FriendsViewModel;
import com.example.project3android.User.User;
import com.example.project3android.User.UserViewModel;

import java.util.List;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3android.Feed.ViewModels.PostsViewModel;
import com.example.project3android.Feed.adapters.PostListAdapter;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.UserViewModel;

public class ProfilePage extends AppCompatActivity {
    private PostListAdapter adapter;
    private PostsViewModel postViewModel;
    private UserViewModel userViewModel;
    private FriendsViewModel friendsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        friendsViewModel = new ViewModelProvider(this).get(FriendsViewModel.class);
        String userId = getIntent().getStringExtra("userId");

        friendsViewModel.getFriends().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

            }
        });

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        postViewModel = new ViewModelProvider(this).get(PostsViewModel.class);

        String fullname = CurrentUser.getInstance().getCurrentUser().getFirstName()
                + " " + CurrentUser.getInstance().getCurrentUser().getLastName();

        //TextView ivName = findViewById(R.id.nameProfilePage);
        //ivName.setText(fullname);

        ImageView ivProfileImage = findViewById(R.id.profileImageProfilePage);
        ivProfileImage.setImageBitmap(CurrentUser.getInstance()
                .getCurrentUser().getBitmapProfileImage());

        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        adapter = new PostListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        postViewModel.get().observe(this, posts -> adapter.setPosts(posts));
    }
}
