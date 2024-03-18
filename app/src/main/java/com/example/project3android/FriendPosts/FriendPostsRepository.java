package com.example.project3android.FriendPosts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.MyApplication;
import com.example.project3android.User.Data.UserAppDB;
import com.example.project3android.User.UserDao;

import java.util.List;

public class FriendPostsRepository {
    private UserDao dao;
    private FriendPostsRepository.FriendPostListData postListData;
    private FriendPostsAPI api;

    public FriendPostsRepository() {
        UserAppDB db = Room.databaseBuilder(MyApplication.context,
                        UserAppDB.class, "UserDB")
                .allowMainThreadQueries().build();
        dao = db.userDao();
        postListData = new FriendPostsRepository.FriendPostListData();
        api = new FriendPostsAPI(postListData, dao);
    }

    class FriendPostListData extends MutableLiveData<List<Post>> {
        public FriendPostListData() {
            super();
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                api.getUserPost(FriendId.getInstance().getfId());
            }).start();
        }
    }
    public LiveData<List<Post>> getAll() {
        return postListData;
    }

    public void reload() {
        api.getUserPost(FriendId.getInstance().getfId());
    }

}
