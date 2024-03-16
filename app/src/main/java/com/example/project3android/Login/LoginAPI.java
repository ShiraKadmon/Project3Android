package com.example.project3android.Login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.project3android.Feed.data.HashMapConverter;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.User.API.UserWebServiceAPI;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.User;
import com.example.project3android.User.UserDao;

import java.util.HashMap;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {
    private MutableLiveData<String> isSucceeded;
    private UserDao dao;
    Retrofit retrofit;
    UserWebServiceAPI webServiceAPI;

    public LoginAPI(MutableLiveData<String> isSucceeded, UserDao dao) {
        this.isSucceeded = isSucceeded;
        this.dao = dao;

        retrofit = new Retrofit.Builder().baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(UserWebServiceAPI.class);
    }

    public void getJwt(User user) {
        HashMap<String, String> hash = HashMapConverter.loginHashMap(user);
        Call<LoginResponse> call = webServiceAPI.getJwt(hash);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse jwtResponse = response.body();
                    CurrentUser.getInstance().setJwtToken(jwtResponse.getToken());
                    CurrentUser.getInstance().setId("65f2c58af7c657942338476d");
                    Log.d("LOGIN_API_RESPONSE", response.body().getToken() + " " + response.body().getId());
                    isSucceeded.postValue("succeeded");
                } else {
                    if (response.code() == 401) {
                        isSucceeded.postValue("Wrong Username or Password");
                    } else {
                        isSucceeded.postValue("Login Failed");
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                isSucceeded.postValue("Internet Connection Problem");
            }
        });
    }
}
