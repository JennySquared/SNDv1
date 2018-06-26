/*
Name: Jenny Hua
Date: March 20, 2018
Title: Register Email
Description: Email screen for the general registration process, takes in the user's email
 */
package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterEmail extends AppCompatActivity {

    public static String email, emailTest, emailPattern = "[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+";
    public EditText emailText;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

       //Instantiate textField
        emailText = findViewById(R.id.emailEdit);
        configureNextButton();
    }

    /*
    Upon clicking on the submit button this method checks to see if the
    user's entry is in the correct format before assigning it to the
    corresponding variable and switching to the next screen
     */
    public void configureNextButton(){
        Button submitButton= findViewById(R.id.submitButton);
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
            }
        });
    }

    public void validateEmail(String eTest){ //Checks to see if entry is in the correct format
        flag = eTest.matches(emailPattern) && eTest.length() > 0;
    }
    private void setEmail(String e){
        email = e;
    }
}