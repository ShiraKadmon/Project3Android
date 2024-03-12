package com.example.project3android.Feed.Post;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.project3android.Feed.Comment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String profileImage;
    private String name;
    private String date;
    private String text;
    private int likes;
    private boolean isLiked;
    private int commentsSize;
    private String pic;
    //private List<Comment> comments;


    public Post(){
        this.name = null;
        this.text = null;
        this.pic = null;
        this.likes = 0;
        this.date = null;
        this.profileImage = null;
        //this.comments = new ArrayList<>();
        this.commentsSize = 0;
        this.isLiked = false;
    }

    public Post(String author, String content, Bitmap pic, String date, Bitmap profilePic, List<Comment> comments) {
        this.name = author;
        this.text = content;
        //this.pic = pic;
        this.likes = 0;
        this.date = date;
        //this.profileImage = profilePic;
        //this.comments = comments;
        this.commentsSize = comments.size();
        this.isLiked = false;
    }

    public Post(String author, String content, String pic, String date, String profilePic, List<Comment> comments) {
        this.name = author;
        this.text = content;
        this.pic = pic;
        this.likes = 0;
        this.date = date;
        this.profileImage = profilePic;
        //this.comments = comments;
        this.commentsSize = comments.size();
        this.isLiked = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    /* public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    } */

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setCommentsSize(int commentsSize) {
        this.commentsSize = commentsSize;
    }

    /* public void setPic(Bitmap pic) {
        this.pic = pic;
    } */

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setComments(List<Comment> comments) {
        //this.comments = comments;
        this.commentsSize = comments.size();
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getText() {
        return this.text;
    }

    public String getLikesString() {
        return Integer.toString(likes) + " Likes";
    }

    public int getLikes() {
        return this.likes;
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

    public Bitmap getBitmapPic() {
        return BitmapFactory.decodeFile(pic);
    }

    public void addLike() {
        this.likes++;
    }

    public void removeLike() {
        this.likes--;
    }

    public void addComment(Comment comment) {
        //comments.add(comment);
        this.commentsSize++;
    }

    public String getDate() {
        return date;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public Bitmap getBitmapProfileImage() {
        return BitmapFactory.decodeFile(profileImage);
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
