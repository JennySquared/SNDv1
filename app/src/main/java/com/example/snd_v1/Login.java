package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
Title: Login / Main Activity
Author: Jenny Shen
Date: April 6, 2018
Description: First Screen user see when opening the app, can login or register
 */

public class Login extends AppCompatActivity {

    public String[] num = new String[1];//retrieve number of babysitters for parent home screen
    public EditText email, password;// login textfields

    //Main Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);//set layout to login layout

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        FirebaseDatabase database = FirebaseDatabase.getInstance();//initialize database
        DatabaseReference myRef = database.getReference();//initialize database reference

        //retrieve number of babysitters
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                num[0] = dataSnapshot.child("numBabysitters").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void log(View view) {
        final View v = view;

        FirebaseDatabase database = FirebaseDatabase.getInstance();//initialize database
        final DatabaseReference myRef = database.getReference("Users"); //initialize database reference

        final String username =email.getText().toString();//String stores text from email textfield
        final String pass = password.getText().toString();//String stores text from password textfield

        //retrieve password and username values from Firebase and check with the login textfields
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    for(int i = 0; i<(int)dataSnapshot.child("Parent").getChildrenCount();i++){
                        if(username.equals(dataSnapshot.child("Parent").child(i+1+"").child("email").getValue(String.class))){
                            if(pass.equals(dataSnapshot.child("Parent").child(i+1+"").child("password").getValue(String.class))){
                                pLogin(v, i+1);
                            }
                        }
                    }
                    for(int i = 0; i<(int)dataSnapshot.child("Babysitter").getChildrenCount();i++){
                    if(username.equals(dataSnapshot.child("Babysitter").child(i+1+"").child("email").getValue(String.class))){
                        if(pass.equals(dataSnapshot.child("Babysitter").child(i+1+"").child("password").getValue(String.class))){
                            bbLogin(v, i+1);
                        }
                    }
                }
                //if login is unsucessful
                Toast.makeText(getApplicationContext(), "Invalid Login" ,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //when user clicks the Register button,screen changes to the name register screen
    public void register(View view) {
        Intent intent = new Intent(this, RegisterName.class);
        startActivity(intent);
    }

    //if babysitter login successful, switches screen to babysitter home
    public void bbLogin(View view, int id) {
        Intent intent = new Intent(this, BabysitterHome.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    //if parent login successful, switches screen to parent home
    public void pLogin(View view, int id) {
        Intent intent = new Intent(this, ParentHome.class);
        intent.putExtra("n",num[0]);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}



