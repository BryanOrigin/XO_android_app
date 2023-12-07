package com.example.my_xo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my_xo.data.myDbHandler;

public class unPage extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_page);

         myDbHandler db = new myDbHandler(unPage.this);
         Button btnLoad = findViewById(R.id.btnLoad);
         EditText et1 = findViewById(R.id.etp1);
         EditText et2 = findViewById(R.id.etp2);



         btnLoad.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String userName1 = et1.getText().toString();
                 String userName2 = et2.getText().toString();

                 if(userName1.equals("") || userName2.equals("") || userName1.equals(userName2)){
                     Toast.makeText(unPage.this,"Enter valid usernames",Toast.LENGTH_SHORT).show();
                     return;
                 }

                 int affectedRow = db.checkIfUsername(userName1,userName2);
                 if(affectedRow >=  1){
                     Toast.makeText(unPage.this,"Username Taken",Toast.LENGTH_SHORT).show();
                     return;
                 }

                 Intent intent = new Intent(unPage.this,MainActivity.class);
                 intent.putExtra("playerName1",userName1);
                 intent.putExtra("playerName2",userName2);
                 startActivity(intent);
                 finish();
             }
         });

    }
}