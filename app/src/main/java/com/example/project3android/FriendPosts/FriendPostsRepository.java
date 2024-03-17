package com.example.project3android.FriendPosts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.project3android.Feed.Post.API.PostAPI;
import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostDao;
import com.example.project3android.Feed.data.AppDB;
import com.example.project3android.MyApplication;

import java.util.List;

public class FriendPostsRepository {
    private PostDao dao;
    private FriendPostsRepository.PostListData postListData;
    private PostAPI api;
    private String userId;

    public FriendPostsRepository(String id) {
        AppDB db = Room.databaseBuilder(MyApplication.context,
                        AppDB.class, "FeedDB")
                .allowMainThreadQueries().build();
        dao = db.postDao();
        postListData = new FriendPostsRepository.PostListData();
        api = new PostAPI(postListData, dao);
        userId = id;
    }

    class PostListData extends MutableLiveData<List<Post>> {
        public PostListData() {
            super();
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                //postListData.postValue(dao.index());
                api.getUserPost(userId);
            }).start();
        }
    }
    public LiveData<List<Post>> getAll() {
        return postListData;
    }

    public void reload() {
        api.getUserPost(userId);
    }

}
