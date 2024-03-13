package com.example.project3android.Repositories;

import androidx.room.Room;

import com.example.project3android.API.LoginAPI;
import com.example.project3android.API.PostAPI;
import com.example.project3android.Feed.data.AppDB;
import com.example.project3android.MyApplication;

public class LoginRepository {
    private LoginAPI api;

    public LoginRepository() {
        AppDB db = Room.databaseBuilder(MyApplication.context,
                        AppDB.class, "FeedDB")
                .allowMainThreadQueries().build();
        api = new LoginAPI();
    }
}
