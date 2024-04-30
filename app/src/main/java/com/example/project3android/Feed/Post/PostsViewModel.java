package com.example.project3android.Feed.Post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class PostsViewModel extends ViewModel {
    private PostRepository mRepository;
    private LiveData<List<Post>> posts;
    private LiveData<String> state;

    public PostsViewModel() {
        this.mRepository = new PostRepository();
        this.posts = mRepository.getAll();
        this.state = mRepository.get();
    }

    public LiveData<List<Post>> getAll() {
        return posts;
    }

    public LiveData<String> get() {
        return state;
    }

    public void add(Post post) {
        mRepository.add(post);
        this.state = mRepository.get();
    }

    public void delete(Post post) {
        mRepository.delete(post);
    }

    public void update(Post post) {
        mRepository.update(post);
        this.state = mRepository.get();
    }

    public void reload() {
        mRepository.reload();
    }
}
