package com.example.project3android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.project3android.User.Friends.FriendsViewModel;
import com.example.project3android.User.User;
import com.example.project3android.User.UserViewModel;

import java.util.List;

public class ProfilePage extends AppCompatActivity {
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
    }
}
