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
}
