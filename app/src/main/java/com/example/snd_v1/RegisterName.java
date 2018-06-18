/*
Name: Jenny Hua
Date: March 20, 2018
Title: Register Name
Description: Name screen for the general registration process; takes in the user's first and last names
 */
package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterName extends AppCompatActivity {
    public static String name;
    public EditText firstName, lastName;
    boolean entryFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Instantiate textFields
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        configureNextButton();
    }

    /*
    Upon the user clicking the submit button this method checks to see if the
    textFields are left blank or not before assigning the user's entries to
    the corresponding variable and switching to the next screen
     */
    private void configureNextButton(){

        Button submitButton= findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                entryChecks(firstName.getText().toString(), lastName.getText().toString());

                if(entryChecks(firstName.getText().toString(), lastName.getText().toString())){
                    setName(firstName.getText().toString(), lastName.getText().toString());
                    startActivity(new Intent(RegisterName.this, RegisterEmail.class));
                }
            }
        });
    }

    public boolean entryChecks(String first, String last){
        if(first.matches("")){
            firstName.setError("Invalid entry");
            entryFlag = false;
        }

        if (last.matches("")){
            lastName.setError("Invalid Entry");
            entryFlag = false;
        }
        if(!first.matches("")&&!last.matches("")){
            entryFlag = true;
        }

        return entryFlag;
    }

    public void setName(String n, String l){name = n + " " + l;}
}