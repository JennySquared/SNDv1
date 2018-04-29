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