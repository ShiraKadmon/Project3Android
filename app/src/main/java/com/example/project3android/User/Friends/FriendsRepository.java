package com.example.project3android.User.Friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

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
                .allowMainThreadQueries().build();
        dao = db.userDao();
        friendsListData = new FriendsRepository().friendsListData;
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

    public void delete(final User user) {
        //api.delete(post);
    }

    public void reload() {
        //api.get();
    }
}
