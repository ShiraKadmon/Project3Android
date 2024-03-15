package com.example.project3android;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3android.Feed.FeedData;
import com.example.project3android.Feed.ViewModels.PostsViewModel;
import com.example.project3android.Feed.adapters.PostListAdapter;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.UserViewModel;

public class ProfilePage extends AppCompatActivity {
    private PostListAdapter adapter;
    private PostsViewModel postViewModel;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        postViewModel = new ViewModelProvider(this).get(PostsViewModel.class);

        String fullname = CurrentUser.getInstance().getCurrentUser().getFirstName()
                + " " + CurrentUser.getInstance().getCurrentUser().getLastName();

        TextView ivName = findViewById(R.id.nameProfilePage);
        ivName.setText(fullname);

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
