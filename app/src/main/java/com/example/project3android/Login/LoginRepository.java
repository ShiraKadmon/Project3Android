package com.example.project3android.Login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.project3android.MyApplication;
import com.example.project3android.User.Data.UserAppDB;
import com.example.project3android.User.User;
import com.example.project3android.User.UserDao;

public class LoginRepository {
    private UserDao dao;

    private LoginRepository.State state;
    private LoginAPI api;

    public LoginRepository() {
        UserAppDB db = Room.databaseBuilder(MyApplication.context,
                        UserAppDB.class, "LoginDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        dao = db.userDao();
        state = new State();
        api = new LoginAPI(state, dao);
    }

    class State extends MutableLiveData<String> {
        public State() {
            super();
        }

        @Override
        protected void onActive() {
            super.onActive();
        }
    }

    public LiveData<String> get() {
        return state;
    }

    public void getJwt(final User user) {
        api.getJwt(user);
    }

}
