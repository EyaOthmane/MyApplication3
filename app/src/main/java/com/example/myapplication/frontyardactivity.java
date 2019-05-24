package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class frontyardactivity extends Activity {
    public String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontyardactivity);
        final SeekBar lighting=findViewById(R.id.humidity);
        final Switch sprinkler=findViewById(R.id.sprinkler);
        ImageButton back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        sprinkler.setClickable(false);

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
                        seekBar.setProgress(originalProgress);
                }


            }
        });
        ImageButton reload=findViewById(R.id.reloadImageButton);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message= "js";
                send sendcode = new send();
                sendcode.execute();
                //TODO get the info and display
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

}private void goBack(){
        Intent backIntent = new Intent(this,layoutactivity2.class);
        startActivity(backIntent);
    }}

