package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<Player> players = new ArrayList<>();
    private static final String TAG = GameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String scores = sharedPreferences.getString("scores", "none");
        if(!scores.equals("none")){
            String[] playerLines = scores.split(",");
            for(String line: playerLines){
                String[] player = line.split(":");
                players.add(new Player(player[0], Integer.parseInt(player[1])));
            }
        }
        recyclerView = findViewById(R.id.recycleView);
        players.sort((a, b)-> b.getScore()-a.getScore());
        MyAdapter myAdapter = new MyAdapter(this, players);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
