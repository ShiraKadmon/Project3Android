package com.example.project3android.SignUp;

import androidx.lifecycle.MutableLiveData;

import com.example.project3android.Feed.data.HashMapConverter;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.User.API.UserWebServiceAPI;
import com.example.project3android.User.User;
import com.example.project3android.User.UserDao;

import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpAPI {
    private MutableLiveData<String> isSucceeded;
    private UserDao dao;
    Retrofit retrofit;
    UserWebServiceAPI webServiceAPI;

    public SignUpAPI(MutableLiveData<String> isSucceeded, UserDao dao) {
        this.isSucceeded = isSucceeded;
        this.dao = dao;

        retrofit = new Retrofit.Builder().baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(UserWebServiceAPI.class);
    }

    public void createUser(User user) {
        // dao.insert(post);
        Call<Void> call = webServiceAPI.createUser(HashMapConverter.signUpHashMap(user));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        dao.insert(user);
                        isSucceeded.postValue("Welcome! \n Please Login");
                    }).start();
                } else {
                    if (response.code() == 403) {
                        isSucceeded.postValue("Username already exist");
                    } else {
                        isSucceeded.postValue("Registration failed");
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isSucceeded.postValue("Internet Connection Problem");
            }
        });
    }
}
