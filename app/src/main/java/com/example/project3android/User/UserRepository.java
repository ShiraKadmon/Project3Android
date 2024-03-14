package com.example.project3android.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.project3android.MyApplication;
import com.example.project3android.User.API.UserAPI;
import com.example.project3android.User.Data.UsersAppDB;

public class UserRepository {
    private UserDao dao;
    private UserRepository.UserData userData;
    private UserAPI api;

    public UserRepository() {
        UsersAppDB db = Room.databaseBuilder(MyApplication.context,
                        UsersAppDB.class, "UsersDB")
                .allowMainThreadQueries().build();
        dao = db.userDao();
        userData = new UserRepository.UserData();
        api = new UserAPI(userData, dao);
    }

    class UserData extends MutableLiveData<User> {
        public UserData() {
            super();

            setValue(dao.get(CurrentUser.getInstance().getJwtToken()));
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> userData.postValue(dao.get(CurrentUser.getInstance().getJwtToken()))).
                    start();
            api.get();
        }
    }
    public LiveData<User> get() {
        return userData;
    }

}