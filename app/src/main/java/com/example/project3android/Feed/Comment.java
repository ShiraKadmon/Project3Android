package com.example.project3android.Feed;

import java.io.Serializable;

public class Comment implements Serializable {
    private int authorId;
    //private final String name;
    private String comment;

    public Comment(String author, String content) {
        //this.authorId = author;
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
}
