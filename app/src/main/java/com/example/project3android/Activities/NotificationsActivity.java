package com.example.project3android.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3android.Feed.adapters.PostListAdapter;
import com.example.project3android.FriendsRequest.FriendsRequest;
import com.example.project3android.FriendsRequest.FriendsRequestListAdapter;
import com.example.project3android.R;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.Friends.FriendsViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {
    private FriendsViewModel friendsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        friendsViewModel = new ViewModelProvider(this).get(FriendsViewModel.class);

        FriendsRequestListAdapter adapter = new FriendsRequestListAdapter(this);
        List<FriendsRequest> friendsRequests = new ArrayList<>();
        RecyclerView lstFriendsRequest = findViewById(R.id.lstFriendsRequest);
        lstFriendsRequest.setAdapter(adapter);
        lstFriendsRequest.setLayoutManager(new LinearLayoutManager(this));
        adapter.setFriendsRequests(friendsRequests);

        String fullName = CurrentUser.getInstance().getCurrentUser().getFirstName() + " " +
                CurrentUser.getInstance().getCurrentUser().getLastName();

        TextView ivName = findViewById(R.id.nameProfilePage);
        ivName.setText(fullName);

        ImageView ivProfileImage = findViewById(R.id.profileImageProfilePage);
        ivProfileImage.setImageBitmap(
                CurrentUser.getInstance().getCurrentUser().getBitmapProfileImage());

        ImageButton closeBtn = findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(v -> finish());

        Button homeBtn = findViewById(R.id.home);
        homeBtn.setOnClickListener(v -> finish());
    }

    public void approve(FriendsRequest request) {
        friendsViewModel.approve(CurrentUser.getInstance().getId(), request.getUser().get_id());
    }
}