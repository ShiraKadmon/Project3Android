package com.example.project3android.Feed.Post;


import static com.example.project3android.Image.BitMapClass.loadImageAsync;

import android.graphics.Bitmap;
import android.widget.Button;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.project3android.Feed.Comment;
import com.example.project3android.Image.BitMapClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Post implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String user_id;
    private String author_image;
    private String author_name;
    private String content;
    private String title;
    private int likes_count;
    private int share_count;
    private String commentsJson; // Store comments as JSON string
    private String postId;
    private String date;
    private boolean isLiked;
    private int commentsSize;
    private String pic;

    public Post(){
        this.user_id = null;
        this.author_name = null;
        this.content = null;
        this.pic = null;
        this.date = null;
        this.author_image = null;
        this.title = null;
        this.likes_count = 0;
        this.commentsSize = 0;
        this.isLiked = false;
    }

    public Post(String user_id, String author, String content, Bitmap pic,
                String date, Bitmap profilePic, List<Comment> comments) {
        this.user_id = user_id;
        this.author_name = author;
        this.content = content;
        this.title = author;
        //this.pic = pic;
        this.likes_count = 0;
        this.share_count = 0;
        this.date = date;
        //this.profileImage = profilePic;
        setComments(comments);
        this.commentsSize = comments.size();
    }

    public Post(String user_id, String author, String content, String pic, String date,
                String profilePic, List<Comment> comments) {
        this.user_id = user_id;
        this.author_name = author;
        this.content = content;
        this.pic = pic;
        this.title = author;
        this.likes_count = 0;
        this.share_count = 0;
        this.date = date;
        this.author_image = profilePic;
        setComments(comments);
        this.commentsSize = comments.size();
        this.isLiked = false;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    /* public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    } */

    public void setProfileImage(String profileImage) {
        this.author_image = profileImage;
    }

    public void setName(String name) {
        this.author_name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setText(String text) {
        this.content = text;
    }

    public void setLikes(int likes) {
        this.likes_count = likes;
    }

    public void setCommentsSize(int commentsSize) {
        this.commentsSize = commentsSize;
    }

    /* public void setPic(Bitmap pic) {
        this.pic = pic;
    } */

    public void setComments(List<Comment> comments) {
        Gson gson = new Gson();
        this.commentsJson = gson.toJson(comments);
        this.commentsSize = comments.size();
    }

    public List<Comment> getComments() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Comment>>() {}.getType();
        return gson.fromJson(commentsJson, listType);
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setAuthor_image(String author_image) {
        this.author_image = author_image;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setCommentsJson(String commentsJson) {
        this.commentsJson = commentsJson;
    }

    public String getCommentsJson() {
        return commentsJson;
    }

    public int getId() {
        return this.id;
    }
    public String getUserId() {
        return this.user_id;
    }

    public String getName() {
        return this.author_name;
    }

    public String getText() {
        return this.content;
    }

    public String getLikesString() {
        return Integer.toString(likes_count) + " Likes";
    }

    public int getLikes() {
        return this.likes_count;
    }

    public String getCommentsSizeString() {
        return Integer.toString(commentsSize) + " Comments";
    }

    public int getCommentsSize() {
        return this.commentsSize;
    }

    /*public List<Comment> getComments() {
        return comments;
    }*/

    public String getPic() {
        return pic;
    }

    public void addLike() {
        this.likes_count++;
    }

    public void removeLike() {
        this.likes_count--;
    }

    public void addComment(Comment comment) {
        //comments.add(comment);
        this.commentsSize++;
    }

    public String getDate() {
        return date;
    }

    public String getProfileImage() {
        return author_image;
    }

    public Bitmap getBitmapPic() {
        return loadImageAsync(this.pic);
    }

    public Bitmap getBitmapProfileImage() {
        return loadImageAsync(this.author_image);
    }

    public String getUser_id() {
        return user_id;
    }

    public String getAuthor_image() {
        return author_image;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public String getPostId() {
        return postId;
    }

    public void addComment(String author, String text) {
        //comments.add(new Comment(author, text));
        this.commentsSize++;
    }

    @Ignore
    public boolean isLiked() {
        return isLiked;
    }

    public boolean getIsLiked() {
        return isLiked;
    }

    public void toggleLike(Button likeBtn, int imageLike) {
        if (!isLiked) {
            addLike();
        } else {
            removeLike();
        }
        isLiked = !isLiked;
        likeBtn.setCompoundDrawablesWithIntrinsicBounds(0,imageLike , 0, 0);

    }

    public void refreshCommentsSize(){
        //this.commentsSize = comments.size();
    }
}
