/*
Name: Jenny Hua
Date: March 24, 2018
Title: Register Address
Description: Address screen for the general registration process; takes in the user's street address and postal code
 */
package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterAddress extends AppCompatActivity {


    public static String address, postalPattern = "[a-zA-Z]+[0-9]+[a-zA-Z]+[0-9]+[a-zA-Z]+[0-9]";
    public EditText addressText, postalText;
    boolean addressFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        //Instantiate text fields
        addressText = findViewById(R.id.addressText);
        postalText = findViewById(R.id.postalText);

        configureNextButton();
    }
    /*
        Upon the user pressing the submit button this method checks to see if
        their address and postal code are entered correctly before assigning
        the entries to the corresponding variable and switching to the next screen
    */
    public void configureNextButton() {
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(postalCheck(postalText.getText().toString())&&postalText.getText().toString().length()==6&&addressCheck(addressText.getText().toString())){
                    setAddress(addressText.getText().toString(), postalText.getText().toString());
                    startActivity(new Intent(RegisterAddress.this, RegisterGender.class));
                }
            }
        });
    }

    public boolean postalCheck(String postalTest){ //Checks to se if user's postal code entry is in the proper format
        if(!postalTest.matches(postalPattern)){
            postalText.setError("Invalid entry");
            return false;
        }
        return true;
    }

    public boolean addressCheck(String a){ //Checks to see if the address textField is blank
        if(a.matches("")){
            addressText.setError("Invalid entry"); //If blank, sets an error message prompting user to enter a value
            addressFlag = false;
        }
        else{
            addressFlag= true;
        }
        return addressFlag;
    }
    private void setAddress(String a, String p){ //Mutator method for address
        address = a + ", " + p;
    }
}