package com.example.project3android.SignUp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.project3android.MyApplication;
import com.example.project3android.User.Data.UserAppDB;
import com.example.project3android.User.User;
import com.example.project3android.User.UserDao;

public class SignUpRepository {
    private UserDao dao;
    private SignUpRepository.State state;
    private SignUpAPI api;

    public SignUpRepository() {
        UserAppDB db = Room.databaseBuilder(MyApplication.context,
                        UserAppDB.class, "UsersDB")
                .allowMainThreadQueries().build();
        dao = db.userDao();
        state = new State();
        api = new SignUpAPI(state, dao);
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

    public void add(final User user) {
        api.createUser(user);
    }
}
