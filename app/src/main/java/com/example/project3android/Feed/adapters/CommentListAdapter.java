package com.example.project3android.Feed.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project3android.Feed.Comment;
import com.example.project3android.Activities.Comments;
import com.example.project3android.R;
import com.example.project3android.User.CurrentUser;

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

    private final Comments context;
    // the comments
    private List<Comment> comments;
    private boolean isEditing = false;
    private boolean nightMode = false;

    public CommentListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = (Comments) context;
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

            if (current.getName().equals(CurrentUser.getInstance().getCurrentUser().getUsername())) {
                holder.editComment.setEnabled(true);
                holder.deleteComment.setEnabled(true);

                holder.editComment.setOnClickListener(v -> {
                    context.editComment(v, position);
                });
                holder.deleteComment.setOnClickListener(v -> {
                    context.deleteComment(current);
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
