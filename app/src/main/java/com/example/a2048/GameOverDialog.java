package com.example.a2048;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class GameOverDialog extends AppCompatDialogFragment {
    private int score;

    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    private GameActivity gameActivity;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Game Over")
                .setMessage("Your score: " + score)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameActivity.finish();
                    }
                });
        return builder.create();
    }
    public void setScore(int score){
        this.score = score;
    }
}
