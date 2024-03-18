package com.example.project3android.FriendsRequest;

public class FriendshipStatus {
    private int fid;
    private String user_id;
    private String friend_id;
    private String status;
    private String f_id;

    public FriendshipStatus(String user_id, String friend_id, String status, String f_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
        this.status = status;
        this.f_id = f_id;
    }

    public int getFid() {
        return fid;
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

    public String getF_id() {
        return f_id;
    }

    public void setFid(int id) {
        this.fid = id;
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

    public void setF_id(String _id) {
        this.f_id = _id;
    }
}
