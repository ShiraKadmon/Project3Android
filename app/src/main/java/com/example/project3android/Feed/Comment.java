package com.example.project3android.Feed;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Comment implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int authorId;
    private String user_id;
    //private final String name;
    private String comment;

    public Comment(String author, String content) {
        this.user_id = author;
        this.comment = content;
    }

    public Comment(String content) {
        //this.authorId = author;
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
        return "John Doe";
    }

    public String getComment() {
        return this.comment;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }
}
