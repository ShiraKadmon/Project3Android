package com.example.project3android.Feed;

import android.graphics.Bitmap;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.adapters.PostListAdapter;

import java.util.ArrayList;
import java.util.List;

public class FeedData {
    private String userName;
    private Bitmap profileImage;
    private final static FeedData INSTANCE = new FeedData();
    private List<Post> posts;
    private PostListAdapter adapter;

    // Private constructor suppresses generation of a (public) default constructor
    private FeedData() {
        this.posts = new ArrayList<>();
    }

    public static FeedData getInstance() {
        return INSTANCE;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

    public void setAdapter(PostListAdapter adapter) {
        this.adapter = adapter;
    }

    public List<Post> getPosts() {
        return this.posts;
    }

    public String getUserName() {
        return userName;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void addComment(Comment comment, int position) {
        this.posts.get(position).addComment(comment);
        notifyAdapterSetChanged();
    }

    public void addPost(Post post) {
        this.posts.add(0, post);
        notifyAdapterSetChanged();
    }

    public void replacePost(int position, Post post) {
        this.posts.remove(position);
        this.posts.add(position, post);
        notifyAdapterSetChanged();
    }

    public void notifyAdapterSetChanged() {
        this.adapter.notifyDataSetChanged();
    }
}
