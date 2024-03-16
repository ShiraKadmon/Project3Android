package com.example.project3android.Feed.data;

import com.example.project3android.User.User;

import java.util.HashMap;

public class HashMapConverter {

    public static HashMap<String, String> loginHashMap(User user) {
        HashMap<String, String> hash = new HashMap<>();
        hash.put("email", user.getUsername());
        hash.put("password", user.getPassword());
        return hash;
    }

    public static HashMap<String, String> signUpHashMap(User user) {
        HashMap<String, String> hash = new HashMap<>();
        hash.put("first_name", user.getFirstName());
        hash.put("last_name", user.getLastName());
        hash.put("email", user.getUsername());
        hash.put("password", user.getPassword());
        hash.put("profile_picture", user.getPassword());
        return hash;
    }

    public static HashMap<String, String> getUserHashMap(String id) {
        HashMap<String, String> hash = new HashMap<>();
        hash.put("id", id);
        return hash;
    }
}
