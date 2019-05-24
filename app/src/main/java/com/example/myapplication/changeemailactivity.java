package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

/**
 * A login screen that offers login via email/password.
 */
public class changeemailactivity extends AppCompatActivity  {
    private AutoCompleteTextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changeemailactivity);
        final AutoCompleteTextView email=findViewById(R.id.email);
        final AutoCompleteTextView password = findViewById(R.id.password);
        final EditText passwordRepeat = findViewById(R.id.passwordrepeat);
        password.setError(null);
        passwordRepeat.setError(null);
        email.setError(null);
        Button signIn = findViewById(R.id.changeButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cool=true;
                String memail=email.getText().toString();
                String mpassword=password.getText().toString();
                String mpasswordrepeat=passwordRepeat.getText().toString();
                if(TextUtils.isEmpty(memail)){
                    cool=false;
                    email.setError("This field is required");
                }else if(!emailOK(memail)){
                    cool=false;
                    email.setError("This is not an email");
                }
                if(TextUtils.isEmpty(mpassword)){
                    cool=false;
                    password.setError("This field is required");
                }else if(!passwordOK(mpassword)){
                    cool=false;
                    password.setError("This password is too short");
                }else if (TextUtils.isEmpty(mpasswordrepeat)){
                    cool=false;
                    passwordRepeat.setError("This field is required");
                }else if(!passwordsMatch(mpassword,mpasswordrepeat)){
                    cool=false;
                    passwordRepeat.setError("The passwords have to match");
                }
                if(cool){

                    goToLogin();
                }
            }
        });

    }
    private void goToLogin(){
        Intent intent= new Intent (this, loginactivity.class);
        startActivity(intent);
    }
    private boolean passwordsMatch(String password, String passwordRepeat){
        return(password.equals(passwordRepeat));
    }
    private boolean passwordOK(String password){
        return(password.length()>6);
    }
    private boolean emailOK(String email){
        return(email.contains("@") && email.contains("."));
    }
}
