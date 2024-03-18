package com.example.project3android.FriendsRequest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.project3android.FriendPosts.FriendId;
import com.example.project3android.MyApplication;
import com.example.project3android.User.API.UserAPI;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.Data.UserAppDB;
import com.example.project3android.User.UserDao;
import com.example.project3android.User.UserRepository;
import com.example.project3android.User.UserResponse;

public class FriendsRequestRepository {

    private UserDao dao;
    private FriendsRequestRepository.UserData userData;
    private FriendsRequestAPI api;

    public FriendsRequestRepository() {
        UserAppDB db = Room.databaseBuilder(MyApplication.context,
                        UserAppDB.class, "UsersDB")
                .allowMainThreadQueries().addMigrations(UserAppDB.MIGRATION_1_2).build();
        dao = db.userDao();
        userData = new FriendsRequestRepository.UserData();
        api = new FriendsRequestAPI(userData);
    }

    class UserData extends MutableLiveData<UserResponse> {
        public UserData() {
            super();

            setValue(dao.get(FriendId.getInstance().getfId()));
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                userData.postValue(dao.get(FriendId.getInstance().getfId()));
                Log.d("FRIEND_REPO_THREAD", "after useData");
                api.get();
                Log.d("FRIEND_REPO_THREAD", "after api.get");
            }).start();
        }
    }
    public LiveData<UserResponse> get() {
        return userData;
    }
}
