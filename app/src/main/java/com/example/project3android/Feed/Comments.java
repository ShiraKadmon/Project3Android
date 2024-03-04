package com.example.project3android.Feed;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3android.Feed.FeedData;
import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.adapters.CommentListAdapter;
import com.example.project3android.R;

import java.util.List;

public class Comments extends AppCompatActivity {

    private Post post;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        String username = FeedData.getInstance().getUserName();
        // Retrieve post from post list in data
        post = FeedData.getInstance().getPosts().get(
                getIntent().getIntExtra("position", 0));
        // Retrieve post's comments
        List<Comment> comments = post.getComments();

        // Show user profile image from data
        Bitmap profileImage = FeedData.getInstance().getProfileImage();
        ImageView userProfileImage = findViewById(R.id.userProfileImage);
        userProfileImage.setImageBitmap(profileImage);

        if (getIntent().getBooleanExtra("nightMode", false)) {
            findViewById(R.id.topLayout).setBackgroundColor(Color.BLACK);
            findViewById(R.id.mainLayout).setBackgroundColor(Color.BLACK);
            findViewById(R.id.addCommentLayout).setBackgroundColor(Color.BLACK);
        }

        if (getIntent().getBooleanExtra("nightMode", false)) {
            findViewById(R.id.topLayout).setBackgroundColor(Color.BLACK);
            findViewById(R.id.mainLayout).setBackgroundColor(Color.BLACK);
            findViewById(R.id.addCommentLayout).setBackgroundColor(Color.BLACK);
        }

        RecyclerView lstComments = findViewById(R.id.lstComments);
        final CommentListAdapter commentsAdapter = new CommentListAdapter(this);
        lstComments.setAdapter(commentsAdapter);
        lstComments.setLayoutManager(new LinearLayoutManager(this));

        // Set comments to CommentListAdapter
        commentsAdapter.setComments(comments);

        TextView tvAuthor = findViewById(R.id.tvAuthor);
        TextView date = findViewById(R.id.date);
        ImageView profilePic = findViewById(R.id.profileImage);

        tvAuthor.setText(post.getName());
        date.setText(post.getDate());
        profilePic.setImageBitmap(post.getProfileImage());

        // Close button
        ImageButton closeBtn = findViewById(R.id.close_comments);
        closeBtn.setOnClickListener(v -> {
            /*
            Intent resultIntent = new Intent();
            resultIntent.putExtra("post", (Serializable) post);
            resultIntent.putExtra("position",
                    getIntent().getIntExtra("position", 0));
            setResult(Activity.RESULT_OK, resultIntent);
            */
            post.refreshCommentsSize();
            finish();
        });


        EditText comment = findViewById(R.id.commentAddText);
        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                comment.setEnabled(!commentsAdapter.isEditing());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                comment.setEnabled(!commentsAdapter.isEditing());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                comment.setEnabled(!commentsAdapter.isEditing());
                String inputText = editable.toString();
                ImageButton addCommentBtn = findViewById(R.id.addCommentBtn);
                // Text is entered, change button color to pressed state
                // No text entered, change button color to normal state
                addCommentBtn.setEnabled(inputText.length() > 0);
            }
        });

        ImageButton addCommentBtn = findViewById(R.id.addCommentBtn);
        addCommentBtn.setEnabled(false);
        addCommentBtn.setOnClickListener(v -> {
            String commentText = comment.getText().toString();
            if (commentText.length() > 0) {
                FeedData.getInstance().addComment(new Comment(username, commentText),
                        getIntent().getIntExtra("position", 0));
                //post.addComment(new Comment(username, commentText));
                commentsAdapter.notifyDataSetChanged();
            }
        });
    }
}

