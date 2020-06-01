package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private String name;
    private TextView score;
    private float x1 = 0, x2, y1 = 0, y2;
    private Integer scoreValue = 0;
    private TextView[] box = new TextView[16];

    private static final String TAG = GameActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent j = getIntent();
        name = j.getStringExtra("name");
        score = (TextView)findViewById(R.id.score);
        box[0] = (TextView)findViewById(R.id.box1);
        box[1] = (TextView)findViewById(R.id.box2);
        box[2] = (TextView)findViewById(R.id.box3);
        box[3] = (TextView)findViewById(R.id.box4);
        box[4] = (TextView)findViewById(R.id.box5);
        box[5] = (TextView)findViewById(R.id.box6);
        box[6] = (TextView)findViewById(R.id.box7);
        box[7] = (TextView)findViewById(R.id.box8);
        box[8] = (TextView)findViewById(R.id.box9);
        box[9] = (TextView)findViewById(R.id.box10);
        box[10] = (TextView)findViewById(R.id.box11);
        box[11] = (TextView)findViewById(R.id.box12);
        box[12] = (TextView)findViewById(R.id.box13);
        box[13] = (TextView)findViewById(R.id.box14);
        box[14] = (TextView)findViewById(R.id.box15);
        box[15] = (TextView)findViewById(R.id.box16);
        setRandom();
        if(name.equals("continue"))
            loadData(j.getStringExtra("game"));
        else{
            SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
            String line = sharedPreferences.getString("currentGame", "none");
            if(!line.equals("none")){
                String[] data = line.split(",");
                saveScore(data[0], Integer.parseInt(data[17]));
            }
        }
        score.setText(scoreValue.toString());
        setVisibility();

    }

    private void loadData(String line) {
        String[] data = line.split(",");
        name = data[0];
        for(int i = 0; i<16; i++){
            box[i].setText(data[i+1]);
        }
        scoreValue = Integer.parseInt(data[17]);
    }

    private boolean isGameOver() {
        for(int i = 0; i<16; i++){
            if(box[i].getText().toString().equals("0"))
                return false;
        }
        return true;

    }

    private void setVisibility() {
        for(int i = 0; i<16; i++){
            if(box[i].getText().toString().equals("0"))
                box[i].setVisibility(View.INVISIBLE);
            else{
                box[i].setVisibility(View.VISIBLE);
                switch (box[i].getText().toString()){
                    case "2":
                        box[i].setBackgroundColor(Color.parseColor("#00BCD4"));
                        box[i].setTextSize(56);
                        break;
                    case "4":
                        box[i].setBackgroundColor(Color.parseColor("#2196F3"));
                        box[i].setTextSize(56);
                        break;
                    case "8":
                        box[i].setBackgroundColor(Color.parseColor("#009688"));
                        box[i].setTextSize(56);
                        break;
                    case "16":
                        box[i].setBackgroundColor(Color.parseColor("#8BC34A"));
                        box[i].setTextSize(56);
                        break;
                    case "32":
                        box[i].setBackgroundColor(Color.parseColor("#FFC107"));
                        box[i].setTextSize(56);
                        break;
                    case "64":
                        box[i].setBackgroundColor(Color.parseColor("#FF5722"));
                        box[i].setTextSize(56);
                        break;
                    case "128":
                        box[i].setBackgroundColor(Color.parseColor("#E91E63"));
                        box[i].setTextSize(36);
                        break;
                    case "256":
                        box[i].setBackgroundColor(Color.parseColor("#F44336"));
                        box[i].setTextSize(36);
                        break;
                    case "512":
                        box[i].setBackgroundColor(Color.parseColor("#9F0B00"));
                        box[i].setTextSize(36);
                        break;
                    case "1024":
                        box[i].setBackgroundColor(Color.parseColor("#000000"));
                        box[i].setTextSize(30);
                        break;
                    case "2048":
                        box[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.buttonshape, null));
                        box[i].setTextSize(30);
                        break;
                    case "4096":
                        box[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.buttonshape, null));
                        box[i].setTextSize(30);
                        break;
                }
            }
        }
    }

    private void setRandom() {
        if(isGameOver()){
            saveScore(name, scoreValue);

            GameOverDialog dialog = new GameOverDialog();
            dialog.setScore(scoreValue);
            dialog.setGameActivity(this);
            dialog.show(getSupportFragmentManager(), "test");
        }else{
            int i = (int)Math.floor(Math.random()*100)+16;
            int j = -1;
            while(i>0){
                j += 1;
                if(j == 16)
                    j = 0;
                if(box[j].getText().toString().equals("0")){
                    i -= 1;
                }
            }
            i = (int)Math.floor(Math.random()*100);
            if(i>80)
                box[j].setText("4");
            else
                box[j].setText("2");
        }

    }

    private void saveScore(String name, Integer scoreValue) {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String scores = sharedPreferences.getString("scores", "none");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(scores.equals("none")){
            editor.putString("scores", name + ":" + scoreValue.toString());
        }else {
            editor.putString("scores", scores + "," + name + ":" + scoreValue.toString());
        }
        editor.commit();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        float a;
        switch (action){
            case (MotionEvent.ACTION_DOWN):
                x1 = event.getX();
                y1 = event.getY();
                break;
            case (MotionEvent.ACTION_UP):
                x2 = event.getX();
                y2 = event.getY();
                a = (y2 - y1)/(x2 - x1);
                if(Math.abs(a)<1){
                    if(x2 - x1 >0)
                        toRight();
                    else
                        toLeft();
                }else{
                    if(y2 - y1 <0)
                        toUp();
                    else
                        toDown();
                }
        }
        return super.onTouchEvent(event);
    }

    private void toUp() {
        int chain = 0;
        boolean isDecalated = false;
        for(int i=0; i<4; i++){
            isDecalated = decalate(12+i, 8+i, 4+i, 0+i) || isDecalated ;
            chain += duplicateAddition(12+i, 8+i, 4+i, 0+i);
            isDecalated = decalate(12+i, 8+i, 4+i, 0+i) || isDecalated;
        }
        if (isDecalated)
            setScore(chain);
        setVisibility();
    }

    private void setScore(int chain) {
        scoreValue += (int)Math.round(Math.pow(chain, chain));
        score.setText(scoreValue.toString());
        setRandom();
    }

    private void toLeft() {
        int chain = 0;
        boolean isDecalated = false;
        for(int i=0; i<16; i+=4){
            isDecalated =  decalate(3+i, 2+i, 1+i, 0+i) || isDecalated;
            chain += duplicateAddition(3+i, 2+i, 1+i, 0+i);
            isDecalated = decalate(3+i, 2+i, 1+i, 0+i) || isDecalated;
        }
        if (isDecalated)
            setScore(chain);
        setVisibility();
    }

    private void toRight() {
        int chain = 0;
        boolean isDecalated = false;
        for(int i=0; i<16; i+=4){
            isDecalated = decalate(0+i, 1+i, 2+i, 3+i) || isDecalated;
            chain += duplicateAddition(0+i, 1+i, 2+i, 3+i);
            isDecalated = decalate(0+i, 1+i, 2+i, 3+i) || isDecalated;
        }
        if (isDecalated)
            setScore(chain);
        setVisibility();
    }

    private void toDown() {
        int chain = 0;
        boolean isDecalated = false;
        for(int i=0; i<4; i++){
            isDecalated =  decalate(0+i, 4+i, 8+i, 12+i) || isDecalated ;
            chain += duplicateAddition(0+i, 4+i, 8+i, 12+i);
            isDecalated =  decalate(0+i, 4+i, 8+i, 12+i) || isDecalated;
        }
        if (isDecalated)
            setScore(chain);
        setVisibility();
    }

    private boolean decalate(int i, int i1, int i2, int i3) {
        List<String> row = Arrays.asList(
                box[i3].getText().toString(),
                box[i2].getText().toString(),
                box[i1].getText().toString(),
                box[i].getText().toString()
        );
        if(row.indexOf("0") == -1 || row.indexOf("0") > lastIndexOfNoneZero(row)){
            return false;
        }
        for(int k =0; k<4; k++){
            if(box[i3].getText().toString().equals("0")){
                box[i3].setText(box[i2].getText().toString());
                box[i2].setText(box[i1].getText().toString());
                box[i1].setText(box[i].getText().toString());
                box[i].setText("0");
            }
            if(box[i2].getText().toString().equals("0")){
                box[i2].setText(box[i1].getText().toString());
                box[i1].setText(box[i].getText().toString());
                box[i].setText("0");
            }
            if(box[i1].getText().toString().equals("0")){
                box[i1].setText(box[i].getText().toString());
                box[i].setText("0");
            }
        }
        return true;
    }

    private int lastIndexOfNoneZero(List<String> row) {
        int index = -1;
        for(int i = 0; i<row.size(); i++){
            if (!row.get(i).equals("0"))
                    index = i;
        }
        return index;
    }

    private int duplicateAddition(int i, int i1, int i2, int i3) {
        int chain = 0;
        if(!box[i2].getText().toString().equals("0")){
            if(addition(i3, i2))
                chain++;
        }

        if(!box[i1].getText().toString().equals("0")){
            if(addition(i2, i1))
                chain++;
        }
        if(!box[i].getText().toString().equals("0")){
            if(addition(i1, i))
                chain++;
        }
        return chain;
    }

    private boolean addition(int i, int i1) {
        if(box[i].getText().toString().equals(box[i1].getText().toString())){
            Integer sum = Integer.parseInt(box[i].getText().toString()) + Integer.parseInt(box[i1].getText().toString());
            box[i].setText(sum.toString());
            box[i1].setText("0");
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentGame", name + ","
                + Arrays.stream(box).map(textView -> textView.getText().toString())
                .reduce((a, b) -> a + "," + b).get() + "," + scoreValue.toString());
        editor.commit();
    }

}
