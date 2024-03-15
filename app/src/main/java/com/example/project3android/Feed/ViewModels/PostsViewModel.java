package com.example.project3android.Feed.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Repositories.PostRepository;

import java.util.List;

public class PostsViewModel extends ViewModel {
    private PostRepository mRepository;
    private LiveData<List<Post>> posts;

    public PostsViewModel() {
        this.mRepository = new PostRepository();
        this.posts = mRepository.getAll();
    }

    public LiveData<List<Post>> get() {
        return posts;
    }

    public void add(Post post) {
        mRepository.add(post);
    }

    public void delete(Post post) {
        mRepository.delete(post);
    }

    public void update(Post post) {
        mRepository.update(post);
    }

    public void reload() {
        mRepository.reload();
    }
}
