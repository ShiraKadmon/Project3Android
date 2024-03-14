package com.example.project3android.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Repositories.PostRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository repository;
    private LiveData<List<User>> friends;

    public UserViewModel() {
        this.repository = new UserRepository();
        this.friends = repository.getAll();
    }

    public LiveData<List<User>> get() {
        return friends;
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public void reload() {
        repository.reload();
    }
}
