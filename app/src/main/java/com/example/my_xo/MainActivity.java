package com.example.my_xo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.my_xo.data.myDbHandler;
import com.example.my_xo.model.Player;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    myDbHandler db = new myDbHandler(MainActivity.this);
    // 0 - x
    // 1 - o
    // 2 - blank
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winPositions = {{0,1,2},{3,4,5},{6,7,8},
                            {0,3,6},{1,4,7},{2,5,8},
                            {0,4,8},{2,4,6}};

    int activePlayer = 0; //setting first move as X

    Boolean gameActive = true;

    int countTap=0;

    String player1; //players names
    String player2;
    int player1_score=0; //players initial score
    int player2_score=0;




    public void playerTap(View view){
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());
        TextView status = findViewById(R.id.status);

        if(gameActive == false){
            gameReset(view);
            return;
        }

        if(gameState[tappedImage] != 2){
            return;
        }

        //setting image if x or 0
        if(gameState[tappedImage] == 2){
            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);
            if(activePlayer==0){
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                status.setText(player2+"'s (O) turn to play");
            }
            else{
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                status.setText(player1+"'s (X) turn to play");
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }


        //checks if anyone won?
        for(int[] winPosition: winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] && gameState[winPosition[1]] == gameState[winPosition[2]] && gameState[winPosition[0]] != 2) {
                gameActive = false;
                if(gameState[winPosition[0]] == 0){
                    status.setText(player1+" (X) has Won");
                    player1_score++;
                    db.updateScore(player1,player1_score);
                    showBoard();
                }
                else{
                    status.setText(player2+" (O) has Won");
                    player2_score++;
                    db.updateScore(player2,player2_score);
                    showBoard();

                }
            }
        }

        countTap++;
        if(countTap==9){
            if(gameActive == true){
                status.setText("It's a draw");
                gameActive = false;
            }
        }

    }

    public void gameReset(View view){
        gameActive = true;
        TextView status = findViewById(R.id.status);
        status.setText(player1+"'s (X) turn to play");
        countTap = 0;
        activePlayer=0;

        for(int i=0; i < gameState.length;i++){
            gameState[i] = 2;
        }

        ((ImageView)(findViewById(R.id.imageView1))).setImageResource(0);
        ((ImageView)(findViewById(R.id.imageView02))).setImageResource(0);
        ((ImageView)(findViewById(R.id.imageView3))).setImageResource(0);
        ((ImageView)(findViewById(R.id.imageView4))).setImageResource(0);
        ((ImageView)(findViewById(R.id.imageView5))).setImageResource(0);
        ((ImageView)(findViewById(R.id.imageView6))).setImageResource(0);
        ((ImageView)(findViewById(R.id.imageView7))).setImageResource(0);
        ((ImageView)(findViewById(R.id.imageView8))).setImageResource(0);
        ((ImageView)(findViewById(R.id.imageView9))).setImageResource(0);
    }


    public void showBoard(){
        ListView scoreBoard = findViewById(R.id.scoreBoard);
        ArrayList<String> scoreBoard_data = new ArrayList<>();

        List<Player> received_scoreBoard = db.Display(player1,player2);

        for(Player pl: received_scoreBoard){
            Log.d("dbbryan","Id: "+pl.getId()+
                    "Name: "+pl.getName()+
                    "Score: "+pl.getScore());
            scoreBoard_data.add(pl.getName()+" score: "+pl.getScore());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,scoreBoard_data);

        scoreBoard.setAdapter(arrayAdapter);
    }

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          player1 = getIntent().getStringExtra("playerName1");
         player2 = getIntent().getStringExtra("playerName2");

        TextView status = findViewById(R.id.status);
        status.setText(player1+"'s (X) turn to play");

       // Log.d("dbbryan","Count: "+db.getCount());

        db.insertPlayers(player1,player1_score,player2,player2_score);

        showBoard();


    }

}