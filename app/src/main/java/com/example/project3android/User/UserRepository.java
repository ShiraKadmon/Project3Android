package com.example.project3android.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.project3android.API.PostAPI;
import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostDao;
import com.example.project3android.Feed.data.AppDB;
import com.example.project3android.MyApplication;
import com.example.project3android.Repositories.PostRepository;
import com.example.project3android.User.API.UserAPI;
import com.example.project3android.User.Data.UsersAppDB;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class UserRepository {
    private UserDao dao;
    private UserRepository.UserListData userListData;
    private UserAPI api;

    public UserRepository() {
        UsersAppDB db = Room.databaseBuilder(MyApplication.context,
                        UsersAppDB.class, "UsersDB")
                .allowMainThreadQueries().build();
        dao = db.userDao();
        userListData = new UserRepository.UserListData();
        api = new UserAPI(userListData, dao);
    }

    class UserListData extends MutableLiveData<List<User>> {
        public UserListData() {
            super();

            setValue(dao.index());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> userListData.postValue(dao.index())).start();
            api.getFriends();
        }
    }
    public LiveData<List<User>> getAll() {
        return userListData;
    }

    public void add(final User user) {
        api.createUser(user);
    }

    public void getJwt(final User user) {
        api.createUser(user);
    }

    public void delete(final User user) {
        //api.delete(post);
    }

    public void reload() {
        //api.get();
    }
}
