package com.example.project3android.Feed;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostDao;

@Database(entities = {Post.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract PostDao postDao();
}
