package com.example.project3android.User.API;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.project3android.Feed.Post.API.TokenInterceptor;
import com.example.project3android.Feed.data.HashMapConverter;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.User.CurrentUser;
import com.example.project3android.User.User;
import com.example.project3android.User.UserDao;
import com.example.project3android.User.UserResponse;

import org.mindrot.jbcrypt.BCrypt;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {

    private MutableLiveData<UserResponse> userData;
    private UserDao dao;
    Retrofit retrofit;
    UserWebServiceAPI webServiceAPI;

    public UserAPI(MutableLiveData<UserResponse> user, UserDao dao) {
        this.userData = user;
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
        Call<UserResponse> call = webServiceAPI.getUser(
                CurrentUser.getInstance().getId());
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                new Thread(() -> {
                    UserResponse userResponse = response.body();
                    userData.postValue(userResponse);
                }).start();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
    }

    public void delete(MutableLiveData<Boolean> data) {
        Call<Void> call = webServiceAPI.deleteUser(CurrentUser.getInstance().getId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                dao.delete(CurrentUser.getInstance().getCurrentUser());
                data.postValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void update(User user, MutableLiveData<Boolean> data) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashedPassword);
        Call<Void> call = webServiceAPI.updateUser(user.get_id(),
                HashMapConverter.signUpHashMap(user));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        dao.update(user);
                        data.postValue(true);
                        //userData.postValue(user.getUserResponse());
                    }).start();
                } else {
                    data.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                data.postValue(false);
            }
        });
    }
}
