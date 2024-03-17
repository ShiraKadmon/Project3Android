package com.example.project3android.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private UserRepository repository;
    private LiveData<UserResponse> user;


    public UserViewModel() {
        this.repository = new UserRepository();
        this.user = repository.get();
    }

    public LiveData<UserResponse> getUser() {
        return user;
    }

    public void delete() {
        repository.delete();
    }

    public void edit(User user) {
        repository.edit(user);
    }

}
