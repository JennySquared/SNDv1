/*
Name: Jenny /hua
Date: March 21, 2018
Title: Register Password
Description: Password screen for the general registration process; takes in the user's created password
 */
package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterPassword extends AppCompatActivity {

    public static String password;
    public EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        //Instantiate textField
        passwordText = (EditText) findViewById(R.id.passwordText);

        configureNextButton();
    }

    /*
     Upon clicking on the submit button this method checks to see if the
     user's entry is in the correct format(at least 8 characters, contains
     one number, one uppercase and one lowercase letter)before assigning
     it to the corresponding variable and switching to the next screen
      */
    private void configureNextButton() {
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(passwordCheck(passwordText.getText().toString())&&passwordText.getText().toString().length()>=8){ //Checks to see if the entry contains all the required types of characters and is longer than 8 characters
                    setPassword(passwordText.getText().toString());
                    startActivity(new Intent(RegisterPassword.this, RegisterBirthday.class));
                }
            }
        });
    }
    public boolean passwordCheck (String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;

        for(int i=0;i < str.length();i++) { //This method uses a fore loop to check each character of the user's entry individually
            ch = str.charAt(i);
            if( Character.isDigit(ch)) { //If the character is a digit, the corresponding boolean flag is set to true
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) { //If the character is an uppercase letter, the corresponding boolean flag is set to true
                capitalFlag = true;
            }

            else if (Character.isLowerCase(ch)) { //If the character is a lowercase letter, the corresponding boolean flag is set to true
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag) //If all three flags have been set to true then the entire method returns true
                return true;
        }
        passwordText.setError("Invalid password"); //If any of the flags are not set to true, textField uses error message to tell the user that their entry is invalid
        return false;
    }
    private void setPassword(String p){ password = p;} //Mutator method for password

}