/*
Name: Jenny Hua
Date: March 22, 2018
Title: Register Birthday
Description: Birthday screen for the general registration process; allows users to enter their date of birth
 */
package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class RegisterBirthday extends AppCompatActivity {

    public static String bday;
    public static int age, year, month, date;
    public DatePicker birthdayPicker;
    Calendar now = Calendar.getInstance(); //Get current date information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        //Takes the current date from the system and assigns to corresponding variables
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);

        //Instantiate datePicker
        birthdayPicker = findViewById(R.id.birthdayPicker);

        configureNextButton();
    }

    private void configureNextButton() {
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setBirthday(birthdayPicker.getDayOfMonth(), birthdayPicker.getMonth()+1, birthdayPicker.getYear());

                age = getAge(year,month, date, birthdayPicker.getYear(), birthdayPicker.getMonth()+1, birthdayPicker.getDayOfMonth());
                if(oldEnough()){
                    startActivity(new Intent(RegisterBirthday.this, RegisterAddress.class));
                }
            }
        });
    }

    public boolean oldEnough (){ //Checks to see if the user is old enough (must be at least 13 years of age) by looking at their age
        if(age<13){
            Toast.makeText(getApplicationContext(), "You must be at least 13 years of age " ,Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void setBirthday(int d, int m, int y){
        bday = d + ", " + m + ", " + y;
    } //Mutator method for birthday string

    public int getAge(int thisYear, int thisMonth, int thisDate, int birthYear, int birthMonth, int birthDate) //Calculates the user's age by comparing their birthday information to the information of the current date
    {
        int age;

        if( (thisMonth==birthMonth&&thisDate<birthDate)||thisMonth<birthMonth)
        {
            age = (thisYear-birthYear)-1;
            return age;
        }

        age = (thisYear-birthYear);
        return age;
    }
}