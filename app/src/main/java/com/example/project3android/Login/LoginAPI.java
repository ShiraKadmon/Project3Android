package com.example.project3android.Login;

import androidx.lifecycle.MutableLiveData;

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
        HashMap<String, String> hash = new HashMap<>();
        hash.put("email", user.getUsername());
        hash.put("password", user.getPassword());
        Call<LoginResponse> call = webServiceAPI.getJwt(hash);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse jwtResponse = response.body();
                    CurrentUser.getInstance().setJwtToken(jwtResponse.getToken());
                    isSucceeded.postValue("succeeded");
                } else {
                    isSucceeded.postValue("Failed to add data:1 " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                isSucceeded.postValue("Failed to add data: " + t.getMessage());
            }
        });
    }
}
