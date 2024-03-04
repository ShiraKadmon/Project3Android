package com.example.project3android.Feed.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project3android.Feed.Comment;
import com.example.project3android.Feed.FeedData;
import com.example.project3android.R;

import java.util.List;

public class CommentListAdapter extends RecyclerView.Adapter<com.example.project3android.Feed.
                                                    adapters.CommentListAdapter.CommentViewHolder>{

    class CommentViewHolder extends RecyclerView.ViewHolder {
        // the comment's data
        private final TextView tvAuthor;
        private final TextView tvContent;
        private final ImageButton editComment;
        private final ImageButton deleteComment;
        private final View commentView;

        // contractor
        private CommentViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.commentAuthor);
            tvContent = itemView.findViewById(R.id.commentText);
            editComment = itemView.findViewById(R.id.edit_comment);
            deleteComment = itemView.findViewById(R.id.delete_comment);
            commentView = itemView;
        }
    }

    private final LayoutInflater mInflater;
    // the comments
    private List<Comment> comments;
    private boolean isEditing = false;
    private boolean nightMode = false;

    public CommentListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public com.example.project3android.Feed.adapters.CommentListAdapter.CommentViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.comment_layout, parent, false);
        return new com.example.project3android.Feed.adapters.CommentListAdapter.
                CommentViewHolder(itemView);
    }

    // set the data
    @Override
    public void onBindViewHolder(com.example.project3android.Feed.adapters.CommentListAdapter.
                                         CommentViewHolder holder, int position) {
        if (comments != null) {
            final Comment current = comments.get(position);
            holder.tvAuthor.setText(current.getName());
            holder.tvContent.setText(current.getComment());
            if (isNightMode()) {
                holder.commentView.setBackgroundColor(Color.BLACK);
            } else {
                holder.commentView.setBackgroundColor(Color.WHITE);
            }

            if (current.getName().equals(FeedData.getInstance().getUserName())) {
                holder.editComment.setEnabled(true);
                holder.deleteComment.setEnabled(true);

                holder.editComment.setOnClickListener(v -> {
                    isEditing = true;
                    // Create and show the share popup window
                    View popupView = mInflater.inflate(R.layout.edit_comment, (ViewGroup) v.getParent(), false);
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
                            current.setComment(commentText);
                            // Dismiss the popup window
                            popupWindow.dismiss();
                            isEditing = false;
                            // Notify the adapter that the data has changed
                            notifyDataSetChanged();
                        }
                    });
                });

                holder.deleteComment.setOnClickListener(v -> {
                    comments.remove(current);
                    notifyDataSetChanged();
                });
            } else {
                holder.editComment.setEnabled(false);
                holder.deleteComment.setEnabled(false);
            }
        }
    }

    // set the comments
    public void setComments(List<Comment> s){
        comments = s;
        notifyDataSetChanged();
    }

    // get the number of the comments
    @Override
    public int getItemCount() {
        if (comments != null)
            return comments.size();
        else return 0;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public boolean isEditing() {
        return isEditing;
    }


    public boolean isNightMode() {
        return nightMode;
    }

    public void setNightMode(boolean nightMode) {
        this.nightMode = nightMode;
        notifyDataSetChanged();
    }
}
