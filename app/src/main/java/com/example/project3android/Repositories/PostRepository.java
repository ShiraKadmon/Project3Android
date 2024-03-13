package com.example.project3android.Repositories;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.project3android.API.PostAPI;
import com.example.project3android.Feed.FeedData;
import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostDao;
import com.example.project3android.Feed.data.AppDB;
import com.example.project3android.Feed.data.PostConverter;
import com.example.project3android.MyApplication;
import com.example.project3android.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class PostRepository {
    private PostDao dao;
    private PostListData postListData;
    private PostAPI api;

    public PostRepository() {
        AppDB db = Room.databaseBuilder(MyApplication.context,
                        AppDB.class, "FeedDB")
                .allowMainThreadQueries().build();
        dao = db.postDao();
        postListData = new PostListData(this);
        api = new PostAPI(postListData, dao);
    }

    class PostListData extends MutableLiveData<List<Post>> {
        private final PostRepository mRepository;

        public PostListData(PostRepository repository) {
            super();
            this.mRepository = repository;

            // Read the JSON file from the raw directory
            /* InputStream inputStream = MyApplication.context.getResources().openRawResource(R.raw.posts);

            // Convert InputStream to String
            String jsonString = convertStreamToString(inputStream);
            PostConverter postConverter = new PostConverter(jsonString);
            List<Post> posts = postConverter.convertJsonToPostList();
            //FeedData.getInstance().setPosts(posts); */
            setValue(dao.index());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                postListData.postValue(dao.index());
                api.get();
            }).start();
        }
    }
    public LiveData<List<Post>> getAll() {
        return postListData;
    }

    public void add(final Post post) {
        api.add(post);
    }

    public void delete(final Post post) {
        api.delete(post);
    }

    public void reload() {
        api.get();
    }

    private String convertStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
