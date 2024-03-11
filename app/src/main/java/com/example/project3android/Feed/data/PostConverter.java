package com.example.project3android.Feed.data;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.TempPost;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class PostConverter {
    private String jsonString;

    public PostConverter(String jsonString) {
        this.jsonString = jsonString;
    }

    public Post convertJsonToPost() {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Post.class);
    }

    public List<Post> convertJsonToPostList() {
        Gson gson = new Gson();
        //List<Post> posts = gson.fromJson(jsonString, new TypeToken<List<Post>>() {}.getType());
        List<Post> posts = gson.fromJson(jsonString, new TypeToken<List<Post>>() {}.getType());

        for (Post post : posts) {
            post.setComments(new CommentConverter(gson.
                    toJson(post.getComments())).convertJsonToCommentList());
        }

        return posts;
    }
}

