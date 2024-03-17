package com.example.project3android.User.Friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.MyApplication;
import com.example.project3android.User.Data.UserAppDB;
import com.example.project3android.User.User;
import com.example.project3android.User.UserDao;

import java.util.List;

public class FriendsRepository {
    private UserDao dao;
    private FriendsRepository.FriendsListData friendsListData;
    private FriendsAPI api;

    public FriendsRepository() {
        UserAppDB db = Room.databaseBuilder(MyApplication.context,
                        UserAppDB.class, "UsersDB")
                .allowMainThreadQueries()
                .addMigrations(UserAppDB.MIGRATION_1_2).build();
        dao = db.userDao();
        friendsListData = new FriendsRepository.FriendsListData();
        api = new FriendsAPI(friendsListData, dao);
    }

    class FriendsListData extends MutableLiveData<List<User>> {
        public FriendsListData() {
            super();
            setValue(dao.index());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> friendsListData.setValue(dao.index())).start();
            api.getFriends();
        }
    }
    public LiveData<List<User>> getAll() {
        return friendsListData;
    }

    public void delete(String userId, String fId) {
        api.delete(userId, fId);
    }

    public void reload() {
        api.getFriends();
    }

    public void add(String userId) {
        api.add(userId);
    }

    public void approve(String userId, String fId) {
        api.approve(userId, fId);
    }

}
