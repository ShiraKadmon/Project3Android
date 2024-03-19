package com.example.project3android.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private UserRepository repository;
    private LiveData<UserResponse> user;
    private MutableLiveData<Boolean> deleteMess;
    private MutableLiveData<Boolean> updateMess;

    public MutableLiveData<Boolean> getDeleteMess() {
        return deleteMess;
    }

    public UserViewModel() {
        this.repository = new UserRepository();
        this.user = repository.get();
        this.deleteMess = new MutableLiveData<>();
        this.updateMess = new MutableLiveData<>();
    }

    public LiveData<UserResponse> getUser() {
        return user;
    }

    public void delete() {
        repository.delete(deleteMess);
    }

    public void edit(User user) {
        repository.edit(user, updateMess);
    }

}
