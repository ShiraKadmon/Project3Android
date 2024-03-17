package com.example.project3android.FriendPosts;

public class FriendId {

    private final static FriendId INSTANCE = new FriendId();
    private String fId;

    public static FriendId getInstance() {
        return INSTANCE;
    }

    private FriendId() {

    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }
}
