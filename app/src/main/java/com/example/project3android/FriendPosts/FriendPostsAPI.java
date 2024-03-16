package com.example.project3android.FriendPosts;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.project3android.API.TokenInterceptor;
import com.example.project3android.API.WebServiceAPI;
import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostDao;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.User.CurrentUser;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendPostsAPI {
    private MutableLiveData<List<Post>> postListData;
    private PostDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public FriendPostsAPI(MutableLiveData<List<Post>> postListData, PostDao dao) {
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
    public void get() {
        Call<List<Post>> call = webServiceAPI.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    // Log the response body

                    new Thread(() -> {
                        postListData.postValue(dao.index());
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
