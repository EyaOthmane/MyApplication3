package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class bathactivity extends AppCompatActivity {
String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bathactivity);
        final Switch manualControl = findViewById(R.id.allowit);
        ImageButton reload = findViewById(R.id.reloadImageButton);
        ImageButton reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message="wok";
                send sendcode = new send();
                sendcode.execute();
            }
        });
        ImageButton back = findViewById(R.id.back);
        final SeekBar lighting= findViewById(R.id.humidity);
        final Switch windowopen = findViewById(R.id.sprinkler);
        ImageButton water= findViewById(R.id.water);
        lighting.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {

            int originalProgress;

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Melek was here...
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                originalProgress = seekBar.getProgress();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int arg1, boolean fromUser) {
                if( fromUser == true) {
                    if (!manualControl.isChecked())
                        seekBar.setProgress(originalProgress);
                }


            }
        });
        final EditText temp= findViewById(R.id.temp);
        if(manualControl.isChecked()){
            //TODO add the part that sends it to the arduino and send it
            int percentage=lighting.getProgress();
            windowopen.setChecked(false);
        }
        else{
            //TODO send to the raspberry that you need to do it yourself
            lighting.setProgress(lighting.getProgress()-10);
            windowopen.setChecked(true);
        }
        if(manualControl.isChecked()){
            windowopen.setClickable(true);
        }else{
            windowopen.setClickable(false);
        }
        manualControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //TODO add the part that sends it to the arduino and send it
                    int percentage=lighting.getProgress();
                    windowopen.setClickable(true);

                }
                else{
                    //TODO send to the raspberry that you need to do it yourself
                    lighting.setProgress(50);
                    windowopen.setClickable(false);


                }
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manualControl.isChecked()){
                    int percentage=lighting.getProgress();




                }
                else{
                    //TODO receive
                    message="ws";
                    send sendcode = new send();
                    sendcode.execute();
                    lighting.setProgress(20);

                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();

            }
        });
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(temp.getText().toString().compareTo("35")>0 || temp.getText().toString().compareTo("15")<0){
                    temp.setError("Please set a temperature between 15 and 35");
                }else{
                    temp.setError(null);
                message="w";
                send sendcode = new send();
                sendcode.execute();
                message= temp.getText().toString();
                sendcode.execute();
            }}
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO request to bring everything back to default
            }
        });

    }
    class send extends AsyncTask<Void,Void,Void> {
        Socket s;
        PrintWriter pw;



        @Override
        protected Void doInBackground(Void... voids) {
            try{
                s=new Socket("raspberrypi",8000);
                pw=new PrintWriter(s.getOutputStream());
                pw.write(message);
                pw.flush();
                pw.close();
                s.close();
            }catch(UnknownHostException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
    private void goBack(){
        Intent backIntent = new Intent(this,layoutactivity2.class);
        startActivity(backIntent);
    }

}


