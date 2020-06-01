package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements BottomModal.ButtonSheetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newGameButton = findViewById(R.id.angry_btn);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomModal modal = new BottomModal();
                modal.show(getSupportFragmentManager(), "enter your name");
            }
        });
        Button continueButton = findViewById(R.id.angry_btn2);
        continueButton.setOnClickListener(v -> onContinue());
        Button scoresButton = findViewById(R.id.angry_btn3);
        scoresButton.setOnClickListener(v-> onScores());
    }

    private void onScores() {
        Intent i = new Intent(this, ScoreActivity.class);
        startActivity(i);
    }

    private void onContinue() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String line = sharedPreferences.getString("currentGame", "none");
        if(!line.equals("none")){
            Intent i = new Intent(this, GameActivity.class);
            i.putExtra("name", "continue");
            i.putExtra("game", line);
            startActivity(i);
        }
    }

    @Override
    public void onButtonClicked(String text) {
        if(text.isEmpty())
            Toast.makeText(getApplicationContext(), "please enter a valid name", Toast.LENGTH_LONG).show();
        else{
            Intent i = new Intent(this, GameActivity.class);
            i.putExtra("name", text);
            startActivity(i);
        }
    }
}
