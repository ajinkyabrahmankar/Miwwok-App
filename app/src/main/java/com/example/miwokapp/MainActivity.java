package com.example.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NumbersClickListner clickListner = new NumbersClickListner();
        //NumbersActivity clickListner = new NumbersActivity();

        TextView numbers = (TextView)findViewById((R.id.numbers));

        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent numbersIntent = new Intent(MainActivity.this,NumbersActivity.class);
                startActivity(numbersIntent);

            }
        });

        TextView family_members = (TextView)findViewById((R.id.family_members));

        family_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent familyIntent = new Intent(MainActivity.this,FamilyMembersActivity.class);
                startActivity(familyIntent);

            }
        });

        TextView colors = (TextView)findViewById((R.id.colors));

        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent colorsIntent = new Intent(MainActivity.this,ColorsActivity.class);
                startActivity(colorsIntent);

            }
        });

        TextView phrases = (TextView)findViewById((R.id.phrases));

        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent phrasesIntent = new Intent(MainActivity.this,PhrasesActivity.class);
                startActivity(phrasesIntent);

            }
        });
    }
}
