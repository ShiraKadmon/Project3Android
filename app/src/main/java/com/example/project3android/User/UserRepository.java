package com.example.project3android.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.project3android.MyApplication;
import com.example.project3android.User.API.UserAPI;
import com.example.project3android.User.Data.UserAppDB;

public class UserRepository {
    private UserDao dao;
    private UserRepository.UserData userData;
    private UserAPI api;

    public UserRepository() {
        UserAppDB db = Room.databaseBuilder(MyApplication.context,
                        UserAppDB.class, "UsersDB")
                .allowMainThreadQueries().addMigrations(UserAppDB.MIGRATION_1_2).build();
        dao = db.userDao();
        userData = new UserRepository.UserData();
        api = new UserAPI(userData, dao);
    }

    class UserData extends MutableLiveData<UserResponse> {
        public UserData() {
            super();

            setValue(dao.get(CurrentUser.getInstance().getId()));
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                userData.postValue(dao.get(CurrentUser.getInstance().getId()));
                api.get();
            }).start();
        }
    }
    public LiveData<UserResponse> get() {
        return userData;
    }

    public void delete(MutableLiveData<Boolean> data) {
        api.delete(data);
    }

    public void edit(User user, MutableLiveData<Boolean> data) {
        api.update(user, data);
        data.observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if (success) {
                    dao.update(user);
                } else {
                }
                // Remove the observer after receiving the result
                data.removeObserver(this);
            }
        });
    }

}
