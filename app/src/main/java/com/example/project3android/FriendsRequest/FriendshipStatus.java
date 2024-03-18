package com.example.project3android.FriendsRequest;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class FriendshipStatus {
    @PrimaryKey(autoGenerate = true)
    private int fid;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("friend_id")
    private String friend_id;
    @SerializedName("status")
    private String status;
    //@SerializedName("f_id")
    //private String f_id;

    public FriendshipStatus(String user_id, String friend_id, String status) {
        this.user_id = user_id;
        this.friend_id = friend_id;
        this.status = status;
        //this.f_id = f_id;
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

    /*public String getF_id() {
        return f_id;
    }*/

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

    /*public void setF_id(String _id) {
        this.f_id = _id;
    }*/
}
