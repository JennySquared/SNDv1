package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterPassword extends AppCompatActivity {

    public String password;
    public EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        passwordText = (EditText) findViewById(R.id.passwordText);


        configureNextButton();
    }

    private void setPassword(String p){
        password = p;
    }

    private void configureNextButton() {
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPassword(passwordText.getText().toString());
                Toast.makeText(getApplicationContext(),"Stored: " + password ,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterPassword.this, RegisterBirthday.class));
            }
        });
    }
}
