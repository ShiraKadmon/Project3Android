package com.example.project3android.FriendsRequest;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.project3android.Feed.Post.API.TokenInterceptor;
import com.example.project3android.Feed.data.HashMapConverter;
import com.example.project3android.FriendPosts.FriendId;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.User.API.UserWebServiceAPI;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.UserResponse;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendsRequestAPI {

    private MutableLiveData<UserResponse> user;
    Retrofit retrofit;
    UserWebServiceAPI webServiceAPI;

    public FriendsRequestAPI(MutableLiveData<UserResponse> user) {
        this.user = user;

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
        Log.d("FRIEND_API", "before call");
        Call<UserResponse> call = webServiceAPI.getUser(
                FriendId.getInstance().getfId());
        Log.d("FRIEND_API", "after call");
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Log.e("FRIEND_API_RESPONSE", "response is successful");
                    new Thread(() -> {
                        //Log.d("FRIEND_API_RESPONSE", response.body().getFriendshipStatus().getStatus());
                        Log.d("FRIEND_API_RESPONSE", response.body().getUser().get_id());
                        Log.d("FRIEND_API_RESPONSE", response.body().getUser().getFirstName());
                        UserResponse userResponse = response.body();
                        //Log.d("FRIEND_API_RESPONSE", response.body().getFriendshipStatus().getStatus());
                        Log.d("FRIEND_API_RESPONSE", response.body().getUser().get_id());
                        Log.d("FRIEND_API_RESPONSE", response.body().getUser().getFirstName());
                        user.postValue(userResponse);
                        //Log.d("FRIEND_API_RESPONSE", response.body().getFriendshipStatus().getStatus());
                        Log.d("FRIEND_API_RESPONSE", response.body().getUser().get_id());
                        Log.d("FRIEND_API_RESPONSE", response.body().getUser().getFirstName());
                    }).start();
                } else {
                    Log.e("FRIEND_API_RESPONSE", "response not successful");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("FRIEND_API_RESPONSE", t.getMessage());
            }
        });
    }
}
