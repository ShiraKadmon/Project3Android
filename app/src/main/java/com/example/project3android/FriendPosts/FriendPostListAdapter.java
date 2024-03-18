package com.example.project3android.FriendPosts;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3android.Activities.ProfilePage;
import com.example.project3android.Feed.Post.Post;
import com.example.project3android.R;

import java.io.Serializable;
import java.util.List;

public class FriendPostListAdapter extends RecyclerView.Adapter<FriendPostListAdapter.FriendPostViewHolder>
        implements Serializable{

    class FriendPostViewHolder extends RecyclerView.ViewHolder implements Serializable {
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
        // contractor
        private FriendPostViewHolder(View itemView) {
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
            likeBtn = itemView.findViewById(R.id.likeBtn);
            likeBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.add_like_selector,
                    0, 0);

        }
    }

    private final LayoutInflater mInflater;
    // the posts
    private List<Post> posts;
    private ProfilePage context;

    public FriendPostListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = (ProfilePage) context;
    }

    @Override
    public FriendPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.post_layout, parent, false);
        return new FriendPostListAdapter.FriendPostViewHolder(itemView);
    }

    // set the data
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull FriendPostViewHolder holder, int position) {
        if (posts != null) {
            final Post current = posts.get(position);
            holder.tvAuthor.setText(current.getName());
            holder.date.setText(current.getDate());
            holder.tvContent.setText(current.getText());
            holder.ivPic.setImageBitmap(current.getBitmapPic());
            holder.likesNum.setText(current.getLikesString());
            holder.commentsNum.setText(current.getCommentsSizeString());
            holder.profilePic.setImageBitmap(current.getBitmapProfileImage());

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

            holder.editBtn.setEnabled(false);
            holder.deleteBtn.setEnabled(false);
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
}
