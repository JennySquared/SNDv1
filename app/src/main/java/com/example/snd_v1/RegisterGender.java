/*
Name: Jenny Hua
Date: March 25, 2018
Title: Register Gender
Description: Gender select screen for the general registration process; takes in the user's gender
 */
package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterGender extends AppCompatActivity implements OnItemSelectedListener{
    public static String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        //Instantiate gender selection spinner
        Spinner spinner = findViewById(R.id.genderDrop);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genderDrop, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        setGender(spinner.getSelectedItem().toString());

        configureNextButton();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text= parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setGender(String g){
        gender = g;
    } //Mutator method for gender

    private void configureNextButton() { //After the user presses the submit button this method sets their selected gender to the corresponding variable and then switches to the next screen
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterGender.this, RegisterBorP.class));
            }
        });
    }
}
