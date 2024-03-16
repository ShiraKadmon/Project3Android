package com.example.project3android.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Repositories.PostRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository repository;
    private LiveData<User> user;


    public UserViewModel() {
        this.repository = new UserRepository();
        this.user = repository.get();
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void delete() {
        repository.delete();
    }

    public void edit(User user) {
        repository.edit(user);
    }

}
