package com.example.project3android.User.API;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Login.LoginRequest;
import com.example.project3android.Login.LoginResponse;
import com.example.project3android.User.User;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserWebServiceAPI {
    @POST("api/tokens")
    Call<LoginResponse> getJwt(@Body HashMap<String, String> body);

    @POST("api/users")
    Call<Void> createUser(@Body HashMap<String, String> body);

    @GET("api/users/{id}")
    Call<User> getUser(@Path("id") String id);

    @PUT("api/users/{id}")
    Call<Void> updateUser(@Body HashMap<String, String> body);

    @DELETE("api/users/{id}")
    Call<Void> deleteUser(@Path("id") String username);

    @GET("api/users/{id}/posts")
    Call<List<Post>> getUserPosts(@Path("id") String username);

    @GET("api/users/{id}/friends")
    Call<List<User>> getUserFriends(@Path("id") String username);

    @POST("api/users/{id}/friends")
    Call<Void> newFriendsRequest(@Path("id") String username);

    @PATCH("api/users/{id}/friends/{fid}")
    Call<Void> approveRequest(@Path("id") String username, @Path("fid") String fUsername);

    @DELETE("api/users/{id}/friends/{fid}")
    Call<Void> deleteFriend(@Path("id") String username, @Path("fid") String fUsername);
}
