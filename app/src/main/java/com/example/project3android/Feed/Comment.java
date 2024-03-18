package com.example.project3android.Feed;


import com.example.project3android.User.User;

import java.io.Serializable;

public class Comment implements Serializable {
    private int authorId;
    private User user_id;
    private String comment;

    public Comment(User author, String content) {
        this.user_id = author;
        this.comment = content;
    }

    public Comment(String content) {
        this.comment = content;
    }

    public void setId(int id) {
        this.authorId = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getName() {
        return this.user_id.getFirstName() + " " +
                this.user_id.getLastName();
    }

    public String getComment() {
        return this.comment;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public User getUser_id() {
        return user_id;
    }
}
