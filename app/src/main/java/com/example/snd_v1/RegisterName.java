package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterName extends AppCompatActivity {
    public String name;
    public EditText firstName, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        configureNextButton();
    }

    private void configureNextButton(){

        Button submitButton= (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                setName(firstName.getText().toString(), lastName.getText().toString());
                Toast.makeText(getApplicationContext(), "Stored " + name, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterName.this, RegisterEmail.class));
            }
        });
    }

    public void setName(String n, String l){name = n + " " + l;}
}
