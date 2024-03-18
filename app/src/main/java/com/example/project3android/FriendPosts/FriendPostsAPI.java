package com.example.project3android.FriendPosts;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.project3android.Feed.Post.API.TokenInterceptor;
import com.example.project3android.Feed.Post.API.WebServiceAPI;
import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostDao;
import com.example.project3android.Feed.Post.PostResponse;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.UserDao;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendPostsAPI {
    private MutableLiveData<List<Post>> postListData;
    private UserDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public FriendPostsAPI(MutableLiveData<List<Post>> postListData, UserDao dao) {
        this.postListData = postListData;
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

        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }
    public void getUserPost(String fid) {
        Call<List<Post>> call = webServiceAPI.getUserPosts(fid);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call,
                                   Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    // Log the response body

                    new Thread(() -> {
                        postListData.postValue(response.body());
                        Log.d("POST_API_RESPONSE", String.valueOf(response.body())
                                + " " + response.body().toString());

                    }).start();
                } else {
                    // Handle unsuccessful response
                    Log.e("POST_API_RESPONSE", "Unsuccessful response: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                // Log the error message
                Log.e("API_Call", "Failed to fetch posts: " + t.getMessage());
            }
        });
    }
}
