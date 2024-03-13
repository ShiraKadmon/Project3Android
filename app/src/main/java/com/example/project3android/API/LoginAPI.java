package com.example.project3android.API;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostDao;
import com.example.project3android.Feed.data.AppDB;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.Repositories.PostRepository;
import com.example.project3android.User.CurrentUser;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    public LoginAPI() {

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

        //retrofit = new Retrofit.Builder().baseUrl(MyApplication.context.getString(R.string.BaseUrl))
        //        .callbackExecutor(Executors.newSingleThreadExecutor())
        //        .addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }
}
