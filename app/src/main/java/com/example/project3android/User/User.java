package com.example.project3android.User;

import static com.example.project3android.Image.BitMapClass.loadImageAsync;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String profileImage;

    public User(String firstName, String lastName, String username, String password, String profileImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
    }

    @Ignore
    public User(String username, String password) {
        this.firstName = null;
        this.lastName = null;
        this.username = username;
        this.password = password;
        this.profileImage = null;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public Bitmap getBitmapProfileImage() {
        return loadImageAsync(profileImage);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
