package com.example.project3android.Activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3android.Feed.Comment;
import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostsViewModel;
import com.example.project3android.Feed.adapters.CommentListAdapter;
import com.example.project3android.MyApplication;
import com.example.project3android.R;
import com.example.project3android.User.CurrentUser;

import java.util.List;

public class Comments extends AppCompatActivity {

    private Post post;
    private PostsViewModel postsViewModel;
    private Boolean isEditing = false;
    private CommentListAdapter commentsAdapter;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);

        String username = CurrentUser.getInstance().getCurrentUser().getUsername();
        // Retrieve post from post list in data
        post = (Post) getIntent().getSerializableExtra("post");
        // Retrieve post's comments
        List<Comment> comments = post.getComments();

        // Show user profile image from data
        Bitmap profileImage = CurrentUser.getInstance().getCurrentUser().getBitmapProfileImage();
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
        commentsAdapter = new CommentListAdapter(this);
        lstComments.setAdapter(commentsAdapter);
        lstComments.setLayoutManager(new LinearLayoutManager(this));

        // Set comments to CommentListAdapter
        commentsAdapter.setComments(comments);

        TextView tvAuthor = findViewById(R.id.tvAuthor);
        TextView date = findViewById(R.id.date);
        ImageView profilePic = findViewById(R.id.profileImage);

        tvAuthor.setText(post.getName());
        date.setText(post.getDate());
        profilePic.setImageBitmap(post.getBitmapProfileImage());

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
            postsViewModel.update(post);
            finish();
        });


        EditText comment = findViewById(R.id.commentAddText);
        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                comment.setEnabled(!isEditing);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                comment.setEnabled(!isEditing);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                comment.setEnabled(!isEditing);
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
                post.addComment(new Comment(post.getUser(), commentText));
                commentsAdapter.notifyDataSetChanged();
            }
        });
    }

    public void editComment(View v, int position, Comment comment) {
        isEditing = true;
        // Create and show the share popup window
        View popupView = LayoutInflater.from(MyApplication.context).
                inflate(R.layout.edit_comment, (ViewGroup) v.getParent(), false);
        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        // Get reference to EditText in popup layout
        EditText editText = popupView.findViewById(R.id.comment_edit_text);
        // Set proper input type
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.requestFocusFromTouch();
        editText.requestFocus();

        // Get reference to ImageButton in popup layout
        ImageButton sendButton = popupView.findViewById(R.id.edit_comment_btn);
        sendButton.setEnabled(false);
        // Check if there is text
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String commentText = s.toString();
                sendButton.setEnabled(!commentText.isEmpty());
            }
        });

        // Set click listener for the send button
        sendButton.setOnClickListener(sendButtonView -> {
            String commentText = editText.getText().toString();
            if (!commentText.isEmpty()) {
                // Set the comment text
                comment.setComment(commentText);
                commentsAdapter.editComment(position, comment);
                post.editComment(position, commentText);
                // Dismiss the popup window
                popupWindow.dismiss();
                isEditing = false;
                // Notify the adapter that the data has changed
                commentsAdapter.notifyDataSetChanged();
            }
        });

    }

    public void deleteComment(Comment comment) {
        post.deleteComment(comment);
    }
}

