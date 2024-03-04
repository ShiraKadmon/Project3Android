package com.example.project3android.Feed;

import java.io.Serializable;

public class Comment implements Serializable {
    private int id;
    private final String name;
    private String comment;

    public Comment(String author, String content) {
        this.name = author;
        this.comment = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getComment() {
        return this.comment;
    }
}
