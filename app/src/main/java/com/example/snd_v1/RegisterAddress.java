package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterAddress extends AppCompatActivity {

    public String address;
    public EditText addressText, postalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        addressText = (EditText) findViewById(R.id.addressText);
        postalText = (EditText) findViewById(R.id.postalText);

        configureNextButton();
    }

    private void configureNextButton() {
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setAddress(addressText.getText().toString(), postalText.getText().toString());
                Toast.makeText(getApplicationContext(),"Stored: " + address ,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterAddress.this, RegisterGender.class));
            }
        });
    }

    public boolean postalCheck(){
        return false;
    }

    private void setAddress(String a, String p){
        address = a + ", " + p;
    }
}
