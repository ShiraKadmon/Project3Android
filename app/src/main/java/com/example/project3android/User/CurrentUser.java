package com.example.project3android.User;

import com.example.project3android.FriendsRequest.FriendshipStatus;

public class CurrentUser {

    private final static CurrentUser INSTANCE = new CurrentUser();
    private User currentUser;
    private String jwtToken;
    private String id;
    private FriendshipStatus friendshipStatus;

    public static CurrentUser getInstance() {
        return INSTANCE;
    }

    private CurrentUser() { }

    public String getJwtToken() {
        return jwtToken;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public FriendshipStatus getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(FriendshipStatus friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public void setCurrentUser(String firstName, String lastName, String username, String password, String profileImage) {
        this.currentUser = new User(firstName, lastName, username, password, profileImage);
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
