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

    private MutableLiveData<List<User>> friendsListData;
    private UserDao dao;
    Retrofit retrofit;
    UserWebServiceAPI webServiceAPI;

    public UserAPI(MutableLiveData<List<User>> friendsListData, UserDao dao) {
        this.friendsListData = friendsListData;
        this.dao = dao;

        retrofit = new Retrofit.Builder().baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(UserWebServiceAPI.class);
    }
    public void getFriends() {
        Call<List<User>> call = webServiceAPI.getUserFriends(
                CurrentUser.getInstance().getCurrentUser().getId());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                new Thread(() -> {
                    dao.insert(response.body());
                    friendsListData.postValue(dao.index());
                }).start();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {}
        });
    }
}
