package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class kitchenactivity extends AppCompatActivity {
    private com.android.volley.toolbox.Volley Volley;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitchenactivity);
        final Switch window = findViewById(R.id.sprinkler);
        final SeekBar lighting= findViewById(R.id.humidity);
        final Switch manual = findViewById(R.id.allowit);
        ImageButton back = findViewById(R.id.back);
        final TextView gaz = findViewById(R.id.gaz);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        ImageButton reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message="kok";
                send sendcode = new send();
                sendcode.execute();

            }
        });
        ImageButton reload = findViewById(R.id.reloadImageButton);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manual.isChecked()){
                    message="kc";
                    send sendcode = new send();
                    sendcode.execute();
                    message= Integer.toString(lighting.getProgress());

                    sendcode.execute();
                    message= Boolean.toString(window.isChecked());

                    sendcode.execute();
                }
                else {
                    message= "ks";
                    send sendcode = new send();
                    sendcode.execute();
                    gaz.setText("50");
                    //TODO wait for response
                }
            }
        });
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(manual.isChecked()){
                window.setClickable(true);
            }
            else{
                window.setClickable(false);
            }
            }
        });
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
                    if (!manual.isChecked())
                        seekBar.setProgress(originalProgress);
                }


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
        Intent Intent = new Intent(this,layoutactivity2.class);
        startActivity(Intent);
    }
}