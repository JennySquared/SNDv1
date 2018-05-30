/*
Name: Jenny /hua
Date:
Title: Parent Profile Registration
Description:
 */
package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterEmail extends AppCompatActivity {

    public static String email, emailTest, emailPattern = "[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+";
    public EditText emailText;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        emailText = (EditText) findViewById(R.id.emailEdit);
        configureNextButton();
    }
    public void configureNextButton(){
        Button submitButton= (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                emailTest = emailText.getText().toString();
                validateEmail(emailTest);
                if(!flag){
                    emailText.setError("Please enter a valid email address");
                }
                else{
                    setEmail(emailTest);
                    startActivity (new Intent(RegisterEmail.this, RegisterPassword.class));
                }
            };
        });
    }

    public boolean validateEmail(String eTest){
        if (eTest.matches(emailPattern) && eTest.length() > 0)
        {
            flag = true;
            return flag;
        }
        else
        {
            flag = false;
            return flag;
        }
    }
    private void setEmail(String e){
        email = e;
    }
}