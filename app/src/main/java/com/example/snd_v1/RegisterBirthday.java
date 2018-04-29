package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class RegisterBirthday extends AppCompatActivity {

    public String bday;
    public Date birthday;
    public DatePicker birthdayPicker;
    Calendar now = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int date = now.get(Calendar.DATE);

        birthdayPicker = (DatePicker) findViewById(R.id.birthdayPicker);

        configureNextButton();
    }

    private void configureNextButton() {
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBirthday(birthdayPicker.getDayOfMonth(), birthdayPicker.getMonth()+1, birthdayPicker.getYear());
                Toast.makeText(getApplicationContext(),"Stored: " + bday ,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterBirthday.this, RegisterAddress.class));
            }
        });
    }

    private void setBirthday(int d, int m, int y){
        bday = d + ", " + m + ", " + y;
    }

    public int getAge(int ty, int tm, int td, int by, int bm, int bd)
    {
        int age;

        if( tm ==bm&&td<bd)
        {
            age = (ty)-(by)-1;
            return age;
        }

        age = (ty-by);
        return age;
    }
}
