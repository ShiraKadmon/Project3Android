package com.example.project3android.FriendsRequest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project3android.Activities.NotificationsActivity;
import com.example.project3android.R;

import java.io.Serializable;
import java.util.List;

public class FriendsRequestListAdapter extends RecyclerView.Adapter<FriendsRequestListAdapter
        .FriendsRequestViewHolder> {

    class FriendsRequestViewHolder extends RecyclerView.ViewHolder implements Serializable {
        private final ImageView profilePic;
        private final TextView userName;
        private final Button approveBtn;

        private FriendsRequestViewHolder(View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profileImage);
            userName = itemView.findViewById(R.id.name);
            approveBtn = itemView.findViewById(R.id.approve);
        }
    }

    private final LayoutInflater mInflater;
    // the posts
    private List<FriendsRequest> friendsRequests;
    private NotificationsActivity context;

    public FriendsRequestListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = (NotificationsActivity) context;
    }

    @Override
    public FriendsRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.friend_request, parent, false);
        return new FriendsRequestListAdapter.FriendsRequestViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(FriendsRequestListAdapter.FriendsRequestViewHolder holder,
                                 int position) {
        if (friendsRequests != null) {
            final FriendsRequest current = friendsRequests.get(position);
            holder.profilePic.setImageBitmap(current.getUser().getBitmapProfileImage());
            holder.userName.setText(current.getUser().getFirstName() + " " + current.getUser()
                    .getLastName());

            holder.approveBtn.setOnClickListener(v -> {
                context.approve(current);
                friendsRequests.remove(position);
                notifyDataSetChanged();
            });


        }
    }

    public void setFriendsRequests(List<FriendsRequest> friendsRequests) {
        this.friendsRequests = friendsRequests;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (friendsRequests != null)
            return friendsRequests.size();
        else return 0;
    }

    public List<FriendsRequest> getFriendsRequests() {
        return friendsRequests;
    }
}
