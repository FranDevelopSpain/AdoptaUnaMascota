package com.tfg.adoptaunamascota.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.users.User;
import com.tfg.adoptaunamascota.views.home.crudAdmin.UserManagementActivity;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private Context context;
    private UserManagementActivity userManagementActivity;
    private int selectedPosition = -1;

    public UserAdapter(List<User> userList, Context context, UserManagementActivity userManagementActivity) {
        this.userList = userList;
        this.context = context;
        this.userManagementActivity = userManagementActivity;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getSelectedUser() {
        if (selectedPosition >= 0 && selectedPosition < userList.size()) {
            return userList.get(selectedPosition);
        }
        return null;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user, userManagementActivity);

        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(Color.parseColor("#EEEEEE"));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView emailTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.user_name);
            emailTextView = itemView.findViewById(R.id.user_email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    setSelectedPosition(position);
                    userManagementActivity.setSelectedUser(userList.get(position));
                }
            });
        }

        public void bind(final User user, final UserManagementActivity activity) {
            nameTextView.setText(user.getName());
            emailTextView.setText(user.getEmail());
        }
    }
}