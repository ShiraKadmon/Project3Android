package com.example.project3android.User.Friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.User.User;

import java.util.List;

public class FriendsViewModel extends ViewModel {
    private FriendsRepository repository;
    private LiveData<List<User>> friends;


    public FriendsViewModel() {
        this.repository = new FriendsRepository();
        this.friends = repository.getAll();
    }

    public LiveData<List<User>> getFriends() {
        return friends;
    }

    public void delete(String userId, String fId) {
        repository.delete(userId, fId);
    }

    public void add(String userId) {
        repository.add(userId);
    }

    public void approve(String userId, String fId) {
        repository.approve(userId, fId);
    }

}
