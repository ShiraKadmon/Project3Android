package com.example.project3android.Feed.data;

import com.example.project3android.Feed.Comment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class CommentConverter {
    private String jsonString;

    public CommentConverter(String jsonString) {
        this.jsonString = jsonString;
    }

    public Comment convertJsonToComment() {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Comment.class);
    }

    public List<Comment> convertJsonToCommentList() {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, new TypeToken<List<Comment>>() {}.getType());
    }
}
