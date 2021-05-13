package com.example.my_quiz20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_quiz20.databinding.RowLeaderboardBinding;

import java.util.ArrayList;

public class LeaderboardsAdapter extends RecyclerView.Adapter<LeaderboardsAdapter.LeaderboardViewHolder>{
    Context context;
    ArrayList<User> users;
 public LeaderboardsAdapter(Context context, ArrayList<User> users){
     this.context=context;
     this.users=users;
 }
    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_leaderboard,parent,false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
User user=users.get(position);
holder.binding.name.setText(user.getName());
holder.binding.coins.setText(String.valueOf(user.getCoins()));
holder.binding.index.setText(String.format("#%d",position+1));
        Glide.with(context)
                .load(user.getProfile())
                .into(holder.binding.imageView11);
    }

    public int getItemCount() {
        return users.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder{
RowLeaderboardBinding binding;
        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=RowLeaderboardBinding.bind(itemView);
        }
    }
}
