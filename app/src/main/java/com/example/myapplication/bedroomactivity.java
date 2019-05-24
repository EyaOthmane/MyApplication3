package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import yuku.ambilwarna.AmbilWarnaDialog;


public class bedroomactivity extends AppCompatActivity {
    public String message;
    int defaultColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bedroomactivity);
        final ImageButton reset = findViewById(R.id.reset);
        final TextView gaz = findViewById(R.id.gaz);
        final Button pickColor=findViewById(R.id.pickColor);
        ImageButton back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        pickColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message="bok";
                send sendcode = new send();
                sendcode.execute();
            }
        });
        final Switch window =findViewById(R.id.sprinkler);
        final Switch manual=findViewById(R.id.allowit);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manual.isChecked()){
                    window.setClickable(false);
                    pickColor.setClickable(false);
                }else{
                    window.setClickable(true);
                    pickColor.setClickable(true);
                }
            }
        });
        ImageButton reload=findViewById(R.id.reloadImageButton);
        final EditText temperature =findViewById(R.id.temp);
        final SeekBar lighting=findViewById(R.id.humidity);
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
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(temperature.getText().toString().compareTo("35")>0 || temperature.getText().toString().compareTo("15")<0){
                    temperature.setError("Please set a temperature between 15 and 35");
                }
                else if(manual.isChecked()){
                    temperature.setError(null);
                    message="bc";
                    send sendcode = new send();
                    sendcode.execute();
                    message= Integer.toString(defaultColor);

                    sendcode.execute();
                    message= temperature.getText().toString();
                    sendcode.execute();
                }
                else {
                    temperature.setError(null);
                    message= "bs";
                    send sendcode = new send();
                    sendcode.execute();
                    gaz.setText("0");
                    //TODO wait for response
                }
                if(gaz.getText().toString().compareTo("0")>0){
                    gaz.setError("Butane Detected");
                }else {
                    gaz.setError(null);
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
    public void openColorPicker(){
        AmbilWarnaDialog colorPicker=new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor=color;
            }
        });
    }

    private void goBack(){
        Intent backIntent = new Intent(this,layoutactivity2.class);
        startActivity(backIntent);
    }

}
