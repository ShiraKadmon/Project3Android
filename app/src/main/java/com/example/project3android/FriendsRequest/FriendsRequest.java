package com.example.project3android.FriendsRequest;

import com.example.project3android.User.User;

public class FriendsRequest {
    private User user;

    public FriendsRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
