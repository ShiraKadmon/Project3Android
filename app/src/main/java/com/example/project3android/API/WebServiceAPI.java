package com.example.project3android.API;

import androidx.room.Delete;

import com.example.project3android.Feed.Post.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @GET("posts")
    Call<List<Post>> getPosts();

    @POST("posts")
    Call<Void> createPost(@Body Post post);

    @DELETE("post/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
