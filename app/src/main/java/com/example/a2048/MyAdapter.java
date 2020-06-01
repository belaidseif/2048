package com.example.a2048;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Player> players;
    Context context;
    public MyAdapter(Context ct, List<Player> players){
        context = ct;
        this.players = players;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.score_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameText.setText(players.get(position).getName());
        holder.scoreText.setText(String.valueOf(players.get(position).getScore()));
        holder.rankText.setText(String.valueOf(position + 1));
        if(position % 2 == 0)
            holder.cardView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, scoreText, rankText;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.name);
            scoreText = itemView.findViewById(R.id.scoreText);
            rankText = itemView.findViewById(R.id.rank);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
