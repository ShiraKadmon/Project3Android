package com.example.project3android.Feed.Post;

import com.example.project3android.Feed.Comment;
import com.example.project3android.User.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostResponse {
    private String _id;
    private User user_id;
    private String author_image;
    private String author_name;
    private Date created_at;
    private String content;
    private String description;
    private String post_image_url;
    private String title;
    private String label;
    private int likes_count;
    private int share_count;
    private List<Comment> comments = new ArrayList<>();
    //private comments;

    public PostResponse(String _id, User user_id, String author_image, String author_name,
                        Date created_at, String content, String description, String post_image_url,
                        String title, String label, int likes_count, int share_count,
                        List<Comment> comments) {
        this._id = _id;
        this.user_id = user_id;
        this.author_image = author_image;
        this.author_name = author_name;
        this.created_at = created_at;
        this.content = content;
        this.description = description;
        this.post_image_url = post_image_url;
        this.title = title;
        this.label = label;
        this.likes_count = likes_count;
        this.share_count = share_count;
        this.comments = comments;
    }

    public String get_id() {
        return _id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public User getUser_id() {
        return user_id;
    }

    public String getAuthor_image() {
        return author_image;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }

    public String getPost_image_url() {
        return post_image_url;
    }

    public String getTitle() {
        return title;
    }

    public String getLabel() {
        return label;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public void setAuthor_image(String author_image) {
        this.author_image = author_image;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setAuthor_name(String author_author) {
        this.author_name = author_author;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPost_image_url(String post_image_url) {
        this.post_image_url = post_image_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Post getPost() {
        return new Post(this._id, this.user_id, this.author_name, this.content,
                this.post_image_url, this.created_at.toString(), this.author_image,
                this.comments);
    }
}
