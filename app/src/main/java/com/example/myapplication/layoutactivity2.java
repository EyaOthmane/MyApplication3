package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;

public class layoutactivity2 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutactivity2);
        ImageButton emailChangeButton =  findViewById(R.id.settingsButton);
        ImageButton kitchenButton = findViewById(R.id.kitchenButton);
        emailChangeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeEmail();
            }
        });
        kitchenButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToKitchen();
            }
        });
        ImageButton bathroomButton= findViewById(R.id.bathroomButton);
        bathroomButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToBathroom();
            }

    });
        ImageButton frontyardButton = findViewById(R.id.yardButton);
        frontyardButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToFrontYard();
            }
        }
        );
        ImageButton livingroomButton = findViewById(R.id.livingButton);
        livingroomButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToLivingRoom();
            }
        });
        ImageButton bedroomButton = findViewById(R.id.bedroomButton);
        bedroomButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToBedroom();
            }
        });
    }
    private void ChangeEmail(){
        Intent Intent= new Intent(this,changeemailactivity.class);
        startActivity(Intent);
    }
    private void GoToKitchen(){
        Intent Intent= new Intent(this,kitchenactivity.class);
        startActivity(Intent);
    }
    private void GoToBathroom(){
        Intent Intent= new Intent(this,bathactivity.class);
        startActivity(Intent);
    }
    private void GoToFrontYard(){
        Intent Intent= new Intent(this,frontyardactivity.class);
        startActivity(Intent);
    }
    private void GoToBedroom(){
        Intent Intent= new Intent(this,bedroomactivity.class);
        startActivity(Intent);
    }
    private void GoToLivingRoom(){
        Intent Intent= new Intent(this,livingroomactivity.class);
        startActivity(Intent);
    }
}
