package com.example.project3android.Login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.project3android.User.User;

public class LoginViewModel extends ViewModel {
    private LoginRepository repository;
    private LiveData<String> state;

    public LoginViewModel() {
        this.repository = new LoginRepository();
        this.state = repository.get();
    }

    public LiveData<String> get() {
        return state;
    }

    public void getJWT(User user) {
        repository.getJwt(user);
    }

}
