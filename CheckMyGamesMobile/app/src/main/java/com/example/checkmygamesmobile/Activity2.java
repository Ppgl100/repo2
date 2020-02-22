package com.example.checkmygamesmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Activity2 extends AppCompatActivity {

    public Button addButton;
    public Button cancelButton;
    public EditText platformText;
    public EditText gameText;
    //public List<String> myData;
    //public String myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);





        addButton = (Button) findViewById(R.id.add) ;
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                platformText = findViewById(R.id.editText);
                //myData = platformText.getText().toString();
                final List<String> myData = new ArrayList<String>();
                myData.add(platformText.getText().toString());
                platformText = findViewById(R.id.editText2);
                myData.add(platformText.getText().toString());

                // put the String to pass back into an Intent and close this activity
                Intent intent = new Intent();
                intent.putExtra("myData", (ArrayList<String>) myData);
                setResult(1, intent);
                finish();
            }
        });

        cancelButton = (Button) findViewById(R.id.cancel) ;
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(0, intent);
                finish();
            }
        });


        if(this.getIntent().getExtras().get("PLATFORM") == null) {
            Bundle extras = this.getIntent().getExtras();
            String plat = extras.getString("Plat");
            String game = extras.getString("Game");

            if (plat != null) {
                setPlatform(plat);
                setGameTitle(game);
            }
        }
        else
        {
            Bundle extras = this.getIntent().getExtras();
            setPlatform(extras.getString("PLATFORM"));
        }
    }

    public void setPlatform(String p)
    {
        EditText pText = findViewById(R.id.editText);
        pText.setText(p);
    }

    public void setGameTitle(String g)
    {
        EditText gText = findViewById(R.id.editText2);
        gText.setText(g);
    }
}
