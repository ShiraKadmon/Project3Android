package com.example.project3android.FriendsRequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.project3android.User.UserResponse;

public class FriendsRequestViewModel extends ViewModel {
    private FriendsRequestRepository repository;
    private LiveData<UserResponse> user;


    public FriendsRequestViewModel() {
        this.repository = new FriendsRequestRepository();
        this.user = repository.get();
    }

    public LiveData<UserResponse> getUser() {
        return user;
    }
}
