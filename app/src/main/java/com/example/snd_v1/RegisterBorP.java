/*
Name: Jenny Hua
Date: March 28, 2018
Title: Register Babysitter or Parent
Description: Screen where users select if they are a parent user or a babysitter
 */
package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RegisterBorP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borp);

        configureParentButton();
        configureSitterButton();
    }

    /*
    Depending on which button the user presses (Babysitter or Parent)
    the program will take them to the corresponding profile creation screen
    */

    private void configureParentButton() {
        Button parentButton = (Button) findViewById(R.id.parentButton);
        parentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterBorP.this, RegisterParentCreate.class));
            }
        });
    }

    private void configureSitterButton() {
        Button babysitterButton = (Button) findViewById(R.id.babysitterButton);
        babysitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterBorP.this, RegisterBabysitterCreate.class));
            }
        });
    }
}