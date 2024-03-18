package com.example.project3android.Feed.Post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.project3android.Feed.Post.API.PostAPI;
import com.example.project3android.Feed.data.AppDB;
import com.example.project3android.MyApplication;

import java.util.List;


public class PostRepository {
    private PostDao dao;
    private PostListData postListData;
    private PostAPI api;

    public PostRepository() {
        AppDB db = Room.databaseBuilder(MyApplication.context,
                        AppDB.class, "FeedDB")
                .allowMainThreadQueries().addMigrations(AppDB.MIGRATION_1_2,
                        AppDB.MIGRATION_2_3).build();
        dao = db.postDao();
        postListData = new PostListData();
        api = new PostAPI(postListData, dao);
    }

    class PostListData extends MutableLiveData<List<Post>> {
        public PostListData() {
            super();
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

    public void update(final Post post) {
        api.update(post);
    }

    public void reload() {
        api.get();
    }

}
