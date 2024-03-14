package com.example.project3android.User.API;

import androidx.lifecycle.MutableLiveData;

import com.example.project3android.API.TokenInterceptor;
import com.example.project3android.API.WebServiceAPI;
import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostDao;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.User;
import com.example.project3android.User.UserDao;

import java.util.List;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {

    private MutableLiveData<User> user;
    private UserDao dao;
    Retrofit retrofit;
    UserWebServiceAPI webServiceAPI;

    public UserAPI(MutableLiveData<User> user, UserDao dao) {
        this.user = user;
        this.dao = dao;

        TokenInterceptor tokenInterceptor = new TokenInterceptor(
                CurrentUser.getInstance().getJwtToken());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(UserWebServiceAPI.class);
    }
    public void get() {
        Call<User> call = webServiceAPI.getUser(
                CurrentUser.getInstance().getId());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                new Thread(() -> {
                    //dao.clear();
                    //dao.insert(response.body());
                    user.postValue(response.body());
                }).start();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {}
        });
    }
}
