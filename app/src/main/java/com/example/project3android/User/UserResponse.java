package com.example.project3android.User;

import androidx.room.Embedded;

import com.example.project3android.FriendsRequest.FriendshipStatus;

public class UserResponse {
    @Embedded
    private User user;
    @Embedded
    private FriendshipStatus friendshipStatus;

    public UserResponse(User user, FriendshipStatus friendshipStatus) {
        this.user = user;
        this.friendshipStatus = friendshipStatus;
    }

    public User getUser() {
        return user;
    }

    public FriendshipStatus getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFriendshipStatus(FriendshipStatus friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }
}
