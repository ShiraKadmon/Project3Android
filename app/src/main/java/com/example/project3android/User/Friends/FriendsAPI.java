package com.example.project3android.User.Friends;

import androidx.lifecycle.MutableLiveData;

import com.example.project3android.Feed.Post.API.TokenInterceptor;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.User.API.UserWebServiceAPI;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.User;
import com.example.project3android.User.UserDao;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendsAPI {
    private MutableLiveData<List<User>> friendsListData;
    private UserDao dao;
    Retrofit retrofit;
    UserWebServiceAPI webServiceAPI;

    public FriendsAPI(MutableLiveData<List<User>> friendsListData, UserDao dao) {
        this.friendsListData = friendsListData;
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
    public void getFriends() {
        Call<List<User>> call = webServiceAPI.getUserFriends(
                CurrentUser.getInstance().getCurrentUser().getUsername());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                new Thread(() -> {
                    //dao.clear();
                    dao.insert(response.body());
                    friendsListData.postValue(dao.index());
                }).start();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {}
        });
    }

    public void add(String userId) {
        Call<Void> call = webServiceAPI.newFriendsRequest(userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                    }).start();
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void delete(String userId, String fId) {
        //dao.delete(post);
        Call<Void> call = webServiceAPI.deleteFriend(userId, fId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        //dao.delete(userId, fId);
                        friendsListData.postValue(dao.index());
                    }).start();
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void approve(String userId, String fId) {
        Call<Void> call = webServiceAPI.approveRequest(userId, fId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
