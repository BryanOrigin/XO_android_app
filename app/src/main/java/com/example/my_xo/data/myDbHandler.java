package com.example.my_xo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.my_xo.model.Player;
import com.example.my_xo.params.Params;

import java.util.ArrayList;
import java.util.List;

public class myDbHandler extends SQLiteOpenHelper {

     public myDbHandler(Context context){
        super(context, Params.DB_NAME,null,Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)  {
         String CREATE = "CREATE TABLE "+Params.TABLE_NAME+"("+
                 Params.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+Params.KEY_NAME+" TEXT,"+
                 Params.KEY_SCORE+" TEXT"+")";

         Log.d("dbbryan","Query being run: "+CREATE);
         db.execSQL(CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //to update score of the winning player
    public void updateScore(String name,int score){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues values = new ContentValues();

         //values.put(Params.KEY_NAME,name);
         values.put(Params.KEY_SCORE,score);

         long rowAffected = db.update(Params.TABLE_NAME,values,Params.KEY_NAME+" =?",
                 new String[] {name});

         Log.d("dbbryan","Row updated : "+rowAffected);

    }

    //to add players initially
    public void  insertPlayers(String name1,int score1,String name2,int score2){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues value1 = new ContentValues();
         ContentValues value2 = new ContentValues();

         value1.put(Params.KEY_NAME,name1);
         value1.put(Params.KEY_SCORE,score1);
         value2.put(Params.KEY_NAME,name2);
         value2.put(Params.KEY_SCORE,score2);

         long row1 = db.insert(Params.TABLE_NAME,null,value1);
         long row2 = db.insert(Params.TABLE_NAME,null,value2);
         db.close();
         Log.d("dbbryan","data entered for row1 PK: "+row1+
                                  "\n data entered for row2 PK: "+row2);


    }

    //to check if the table is created (debug)
    public int getCount(){
         SQLiteDatabase db = this.getReadableDatabase();
         String query = "SELECT * FROM "+Params.TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);

        return  cursor.getCount();
    }

    public int checkIfUsername(String player1,String player2){
         SQLiteDatabase db = this.getReadableDatabase();
         String query = "SELECT * FROM "+Params.TABLE_NAME+" WHERE "+Params.KEY_NAME+" ='"+player1+"' OR "+Params.KEY_NAME+" = '"+player2+"'";

         Cursor cursor = db.rawQuery(query,null);

         return  cursor.getCount();

    }


    //To display scoreBoard
    public List<Player> Display(String player1, String player2){
        List<Player> scoreBoard = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM "+Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(1).equals(player1)  || cursor.getString(1).equals(player2)){
                    Player player = new Player();

                    player.setId(Integer.parseInt(cursor.getString(0)));
                    player.setName(cursor.getString(1));
                    player.setScore(Integer.parseInt(cursor.getString(2)));

                    scoreBoard.add(player);
                }


            }while(cursor.moveToNext());
        }
        return scoreBoard;
    }
}
