package com.example.project3android.FriendPosts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.project3android.Feed.Post.Post;

import java.util.List;

public class FriendPostsViewModel extends ViewModel {
    private FriendPostsRepository mRepository;
    private LiveData<List<Post>> posts;

    public FriendPostsViewModel(String id) {
        this.mRepository = new FriendPostsRepository(id);
        this.posts = mRepository.getAll();
    }

    public LiveData<List<Post>> get() {
        return posts;
    }

    public void reload() {
        mRepository.reload();
    }
}
