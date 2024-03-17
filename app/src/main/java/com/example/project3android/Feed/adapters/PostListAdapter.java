package com.example.project3android.Feed.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project3android.Activities.Feed;
import com.example.project3android.Feed.Post.Post;
import com.example.project3android.R;
import com.example.project3android.User.CurrentUser;

import java.io.Serializable;
import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder>
        implements Serializable{

    class PostViewHolder extends RecyclerView.ViewHolder implements Serializable {
        // the post's data
        private final ImageView profilePic;
        private final TextView tvAuthor;
        private final TextView date;
        private final TextView tvContent;
        private final ImageView ivPic;
        private final TextView likesNum;
        private final TextView commentsNum;
        private final Button commentsBtn;
        private final Button likeBtn;
        private final Button shareBtn;
        private final ImageButton deleteBtn;
        private final ImageButton editBtn;
        private final View postView;
        // contractor
        private PostViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            date = itemView.findViewById(R.id.date);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivPic = itemView.findViewById(R.id.ivPic);
            likesNum = itemView.findViewById(R.id.likesNum);
            commentsNum = itemView.findViewById(R.id.commentsNum);
            profilePic = itemView.findViewById(R.id.profileImage);
            commentsBtn = itemView.findViewById(R.id.commentBtn);
            shareBtn = itemView.findViewById(R.id.shareBtn);
            deleteBtn = itemView.findViewById(R.id.delete_post);
            editBtn = itemView.findViewById(R.id.edit_post);
            postView = itemView;
            likeBtn = itemView.findViewById(R.id.likeBtn);
            likeBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.add_like_selector,
                    0, 0);

        }
    }

    private final LayoutInflater mInflater;
    // the posts
    private List<Post> posts;
    private boolean nightMode = false;
    private Feed context;

    public PostListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = (Feed) context;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.post_layout, parent, false);
        return new PostViewHolder(itemView);
    }

    // set the data
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        if (posts != null) {
            final Post current = posts.get(position);
            holder.tvAuthor.setText(current.getName());
            holder.date.setText(current.getDate());
            holder.tvContent.setText(current.getText());
            holder.ivPic.setImageBitmap(current.getBitmapPic());
            holder.likesNum.setText(current.getLikesString());
            holder.commentsNum.setText(current.getCommentsSizeString());
            holder.profilePic.setImageBitmap(current.getBitmapProfileImage());
            if (isNightMode()) {
                holder.postView.setBackgroundColor(Color.BLACK);
            } else {
                holder.postView.setBackgroundColor(Color.WHITE);
            }

            holder.tvAuthor.setOnClickListener(view -> {
                this.context.profilePage(current.getUser());
            });

            holder.profilePic.setOnClickListener(view -> {
                this.context.profilePage(current.getUser());
            });

            holder.commentsBtn.setOnClickListener(view -> {
                this.context.addComment(current);
                notifyDataSetChanged();
            });

            holder.likeBtn.setOnClickListener(view -> {
                int imageLike = current.isLiked() ?
                        R.drawable.add_like_selector : R.drawable.ic_like_pressed;
                current.toggleLike(holder.likeBtn, imageLike);
                holder.likeBtn.setPressed(current.isLiked());
                notifyDataSetChanged();
            });

            holder.shareBtn.setOnClickListener(v -> {
                // Create and show the share popup window
                View popupView = mInflater.inflate(R.layout.share_layout, null);
                PopupWindow popupWindow = new PopupWindow(popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                // Set touch listener to dismiss the popup window when tapped outside of it
                ImageButton closeButton = popupView.findViewById(R.id.closeBtn);
                closeButton.setOnClickListener(closeView -> popupWindow.dismiss());
            });

            if (current.getUser().get_id().equals(CurrentUser.getInstance().getId())) {
                holder.editBtn.setEnabled(true);
                holder.deleteBtn.setEnabled(true);
                holder.editBtn.setOnClickListener(v -> {
                    context.editPost(current);
                    notifyDataSetChanged();
                });
                holder.deleteBtn.setOnClickListener(v -> {
                    context.deletePost(current);
                    notifyDataSetChanged();
                });
            } else {
                holder.editBtn.setEnabled(false);
                holder.deleteBtn.setEnabled(false);
            }
        }
    }

    // set the posts
    public void setPosts(List<Post> s){
        posts = s;
        notifyDataSetChanged();
    }

    // get the number of the posts
    @Override
    public int getItemCount() {
        if (posts != null)
            return posts.size();
        else return 0;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public boolean isNightMode() {
        return nightMode;
    }

    public void setNightMode(boolean nightMode) {
        this.nightMode = nightMode;
        notifyDataSetChanged();
    }
}