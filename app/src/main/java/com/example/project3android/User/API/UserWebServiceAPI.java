package com.example.project3android.User.API;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.User.User;

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
    Call<String> getJwt(@Body String username, @Body String password);

    @POST("api/users")
    Call<Void> createUser(@Body User user);

    @GET("api/users/{id}")
    Call<User> getUser(@Path("id") int id);

    @PUT("api/users/{id}")
    Call<Void> updateUser(@Body User user);

    @DELETE("api/users/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    @GET("api/users/{id}/posts")
    Call<List<Post>> getUserPosts(@Path("id") int id);

    @GET("api/users/{id}/friends")
    Call<List<User>> getUserFriends(@Path("id") int id);

    @POST("api/users/{id}/friends")
    Call<Void> newFriendsRequest(@Path("id") int id);

    @PATCH("api/users/{id}/friends/{fid}")
    Call<Void> approveRequest(@Path("id") int id, @Path("fid") int fid);

    @DELETE("api/users/{id}/friends/{fid}")
    Call<Void> deleteFriend(@Path("id") int id, @Path("fid") int fid);
}
