package com.example.project3android.SignUp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.project3android.User.User;
import com.example.project3android.User.UserRepository;

import java.util.List;

public class SignUpViewModel extends ViewModel {
    private SignUpRepository repository;
    private LiveData<String> state;

    public SignUpViewModel() {
        this.repository = new SignUpRepository();
        this.state = repository.get();
    }

    public LiveData<String> get() {
        return state;
    }

    public void add(User user) {
        repository.add(user);
        this.state = repository.get();
    }
}
