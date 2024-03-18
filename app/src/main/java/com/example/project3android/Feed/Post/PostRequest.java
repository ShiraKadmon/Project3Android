package com.example.project3android.Feed.Post;

public class PostRequest {
    private String user_id;
    private String author_image;
    private String author_name;
    private String  content;
    private String post_image_url;
    private String title;

    public PostRequest(String user_id, String author_image,
                       String author_name, String content,
                       String post_image_url) {
        this.user_id = user_id;
        this.author_image = author_image;
        this.author_name = author_name;
        this.content = content;
        this.post_image_url = post_image_url;
        this.title = "title";
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAuthor_image() {
        return author_image;
    }

    public void setAuthor_image(String author_image) {
        this.author_image = author_image;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_image_url() {
        return post_image_url;
    }

    public void setPost_image_url(String post_image_url) {
        this.post_image_url = post_image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
