package com.example.project3android.Feed.Post.API;

import androidx.room.Delete;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostRequest;
import com.example.project3android.Feed.Post.PostResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @GET("api/posts")
    Call<List<PostResponse>> getPosts();

    @POST("api/users/{id}/posts")
    Call<Void> createPost(@Path("id") String username, @Body PostRequest postRequest);

    @PUT("api/users/{id}/posts/{pid}")
    Call<Void> updatePost(@Path("id") String username, @Path("pid") String pid,
                          @Body PostResponse postResponse);

    @DELETE("api/users/{id}/posts/{pid}")
    Call<Void> deletePost(@Path("id") String username, @Path("pid") String pid);

    @GET("api/users/{id}/posts")
    Call<List<Post>> getUserPosts(@Path("id") String userId);
}
