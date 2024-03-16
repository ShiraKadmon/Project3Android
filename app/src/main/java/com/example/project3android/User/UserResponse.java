package com.example.project3android.User;

public class UserResponse {
    private User user;
    private String friendshipStatus;

    public UserResponse(User user, String friendshipStatus) {
        this.user = user;
        this.friendshipStatus = friendshipStatus;
    }

    public User getUser() {
        return user;
    }

    public String getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFriendshipStatus(String friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }
}
