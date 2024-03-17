package com.example.project3android.FriendPosts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.project3android.Feed.Post.API.PostAPI;
import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostDao;
import com.example.project3android.Feed.data.AppDB;
import com.example.project3android.MyApplication;
import com.example.project3android.User.API.UserAPI;
import com.example.project3android.User.Data.UserAppDB;
import com.example.project3android.User.UserDao;

import java.util.List;

public class FriendPostsRepository {
    private UserDao dao;
    private FriendPostsRepository.FriendPostListData postListData;
    private FriendPostsAPI api;
    private String userId;

    public FriendPostsRepository() {
        UserAppDB db = Room.databaseBuilder(MyApplication.context,
                        UserAppDB.class, "UserDB")
                .allowMainThreadQueries().build();
        dao = db.userDao();
        postListData = new FriendPostsRepository.FriendPostListData();
        api = new FriendPostsAPI(postListData, dao);
        userId = FriendId.getInstance().getfId();
    }

    class FriendPostListData extends MutableLiveData<List<Post>> {
        public FriendPostListData() {
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
