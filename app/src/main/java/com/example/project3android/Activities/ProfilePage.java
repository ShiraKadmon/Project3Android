package com.example.project3android.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.project3android.FriendPosts.FriendId;
import com.example.project3android.FriendPosts.FriendPostsViewModel;
import com.example.project3android.R;
import com.example.project3android.User.Friends.FriendsViewModel;
import com.example.project3android.User.User;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3android.Feed.adapters.PostListAdapter;
import com.example.project3android.User.CurrentUser;

public class ProfilePage extends AppCompatActivity {
    private PostListAdapter adapter;
    private FriendPostsViewModel friendPostsViewModel;
    private FriendsViewModel friendsViewModel;
    private List<User> friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        friendsViewModel = new ViewModelProvider(this).get(FriendsViewModel.class);
        User user = (User) getIntent().getSerializableExtra("user");
        FriendId.getInstance().setfId(user.get_id());

        friendsViewModel.getFriends().observe(this, users -> friends = users);

        friendPostsViewModel = new ViewModelProvider(this).get(FriendPostsViewModel.class);

        String fullName = user.getFirstName() + " " + user.getLastName();

        TextView ivName = findViewById(R.id.nameProfilePage);
        ivName.setText(fullName);

        ImageView ivProfileImage = findViewById(R.id.profileImageProfilePage);
        ivProfileImage.setImageBitmap(user.getBitmapProfileImage());

        ImageButton closeBtn = findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(v -> finish());

        if (friends.contains(user)) {
            RecyclerView lstPosts = findViewById(R.id.lstPosts);
            adapter = new PostListAdapter(this);
            lstPosts.setAdapter(adapter);
            lstPosts.setLayoutManager(new LinearLayoutManager(this));
            friendPostsViewModel.get().observe(this, posts -> adapter.setPosts(posts));

            Button friendship = findViewById(R.id.friendshipStatus);
            friendship.setText(R.string.delete_from_friends);
            friendship.setOnClickListener(v -> friendsViewModel.delete(
                    CurrentUser.getInstance().getId(), user.get_id()));
        }
        else {
            Button friendship = findViewById(R.id.friendshipStatus);
            friendship.setText(R.string.ask_friendship);
            friendship.setOnClickListener(v -> {
                friendsViewModel.add(user.get_id());
                friendship.setText(R.string.asking_friendship);
            });
        }

    }
}
