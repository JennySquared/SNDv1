package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BabysitterHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_home);
    }
    public void Search(View view) {
        Intent intent = new Intent(this, BabysitterSearch.class);
        startActivity(intent);
    }
    public void Profile(View view) {
        Intent intent = new Intent(this, BabysitterProfile.class);
        startActivity(intent);
    }

    public void Home(View view) {
        Intent intent = new Intent(this, BabysitterHome.class);
        startActivity(intent);
    }

    public void Logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
