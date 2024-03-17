package com.example.project3android.Feed.Post.API;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostDao;
import com.example.project3android.Feed.Post.PostResponse;
import com.example.project3android.Feed.data.HashMapConverter;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.User.CurrentUser;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAPI {
    private MutableLiveData<List<Post>> postListData;
    private PostDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public PostAPI(MutableLiveData<List<Post>> postListData, PostDao dao) {
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

        //retrofit = new Retrofit.Builder().baseUrl(MyApplication.context.getString(R.string.BaseUrl))
        //        .callbackExecutor(Executors.newSingleThreadExecutor())
        //        .addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }
    public void get() {
        Call<List<PostResponse>> call = webServiceAPI.getPosts();
        call.enqueue(new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(Call<List<PostResponse>> call,
                                   Response<List<PostResponse>> response) {
                if (response.isSuccessful()) {
                    // Log the response body

                    new Thread(() -> {
                        dao.clear();
                        List<Post> posts = new ArrayList<>();
                        for (PostResponse postResponse : response.body()) {
                            posts.add(postResponse.getPost());
                        }
                        dao.insert(posts);
                        postListData.postValue(dao.index());
                        Log.d("POST_API_RESPONSE", String.valueOf(response.body())
                                + " " + response.body().toString());

                    }).start();
                } else {
                    // Handle unsuccessful response
                    Log.e("POST_API_RESPONSE", "Unsuccessful response: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<PostResponse>> call, Throwable t) {
                // Log the error message
                Log.e("API_Call", "Failed to fetch posts: " + t.getMessage());
            }
        });
    }

    public void add(Post post) {
        // dao.insert(post);
        //Call<Void> call = webServiceAPI.createPost(post);
        Call<Void> call = webServiceAPI.createPost(post.getPostResponse());
        //Call<Void> call = webServiceAPI.createPost(HashMapConverter.getIdHashMap(post));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        dao.insert(post);
                        postListData.postValue(dao.index());
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

    public void delete(Post post) {
        //dao.delete(post);
        Call<Void> call = webServiceAPI.deletePost(
              CurrentUser.getInstance().getId(), post.getPostId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        dao.delete(post);
                        postListData.postValue(dao.index());
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

    public void update(Post post) {
        Call<Void> call = webServiceAPI.updatePost(
                CurrentUser.getInstance().getId(), post.getPostId(), post.getPostResponse());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        //response.body().setUser()
                        dao.update(post);
                        postListData.postValue(dao.index());
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

    public void getUserPost(String id) {
        Call<List<PostResponse>> call = webServiceAPI.getUserPosts(id);
        call.enqueue(new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(Call<List<PostResponse>> call,
                                   Response<List<PostResponse>> response) {
                if (response.isSuccessful()) {
                    // Log the response body

                    new Thread(() -> {
                        //dao.clear();
                        dao.clear();
                        List<Post> posts = new ArrayList<>();
                        for (PostResponse postResponse : response.body()) {
                            posts.add(postResponse.getPost());
                        }
                        dao.insert(posts);
                        postListData.postValue(dao.index());
                        Log.d("POST_API_RESPONSE", String.valueOf(response.body())
                                + " " + response.body().toString());

                    }).start();
                } else {
                    // Handle unsuccessful response
                    Log.e("POST_API_RESPONSE", "Unsuccessful response: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<PostResponse>> call, Throwable t) {
                // Log the error message
                Log.e("API_Call", "Failed to fetch posts: " + t.getMessage());
            }
        });
    }

}
