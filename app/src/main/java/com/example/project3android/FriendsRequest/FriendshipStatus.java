package com.example.project3android.FriendsRequest;

public class FriendshipStatus {
    private int id;
    private String user_id;
    private String friend_id;
    private String status;
    private String _id;

    public FriendshipStatus(String user_id, String friend_id, String status, String _id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
        this.status = status;
        this._id = _id;
    }

    public int getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public String getStatus() {
        return status;
    }

    public String get_id() {
        return _id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
