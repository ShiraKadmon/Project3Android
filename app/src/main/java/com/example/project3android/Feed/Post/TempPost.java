package com.example.project3android.Feed.Post;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project3android.Feed.Comment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Entity
public class TempPost implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String profileImage;
    private String name;
    private String date;
    private String text;
    private int likes;
    private boolean isLiked = false;
    private int commentsSize;
    private String pic;
    private List<Comment> comments = new ArrayList<>();


    public TempPost() {
        this.name = null;
        this.text = null;
        this.pic = null;
        this.likes = 0;
        this.date = null;
        this.profileImage = null;
        this.comments = null;
        this.commentsSize = 0;
    }

    public TempPost(String author, String content, String pic, String date, String profilePic, List<Comment> comments) {
        this.name = author;
        this.text = content;
        this.pic = pic;
        this.likes = 0;
        this.date = date;
        this.profileImage = profilePic;
        this.comments = comments;
        this.commentsSize = comments.size();
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        this.commentsSize = comments.size();
    }


    public Post convertToPost() {
        Bitmap bmPic = loadImageAsync(this.pic);
        Bitmap bmProfile = loadImageAsync(this.profileImage);
        Post post = new Post(this.name, this.text, bmPic, this.date, bmProfile, this.comments);
        post.setId(this.id);
        post.setLikes(this.likes);
        return post;
    }



    private Bitmap loadImageAsync(String imageUrl) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Bitmap> future = executorService.submit(() -> convertUrlToBitmap(imageUrl));

        try {
            return future.get();
        } catch (Exception e) {
            // Handle exceptions during image loading
            Log.e("TAG", "Error loading image: " + e.getMessage());
            return null;
        } finally {
            // Shutdown the executor to release resources
            executorService.shutdown();
        }
    }


    public Bitmap convertUrlToBitmap(String image) {
        Bitmap bitmap;
        try {
            URL url = new URL(image);
            HttpURLConnection  conn = (HttpURLConnection) url.openConnection();
            InputStream inputStream = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.e("TempPost", "Error downloading image: " + e.getMessage());
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;

    }
}