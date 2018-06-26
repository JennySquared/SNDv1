package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/*
Title: Babysitter Profile
Author: Jenny Shen
Date: April 13, 2018
Description: Babysitter can view their information and what their profile looks like
 */

public class BabysitterProfile extends AppCompatActivity {

    //main method
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_profile);//set layout to babysitter xml
        final int tag = (getIntent().getExtras().getInt("id"));//get babysitter id

        //get imageview from the xml
        final ImageView profileImageView = findViewById(R.id.imageView2);

        //retrieve image from Firebase Storage
        try {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference bb = storageReference.child(tag+"b.jpg");
        Glide.with(this.getApplicationContext()).load(bb).into(profileImageView);
        }
        catch(Exception e){
        Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
        }

        //initialize Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users/Babysitter"); //set Database reference

        //retrieve babysitter attribute values from database and set them to the corresponding textfields
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child(tag+"").child("name").getValue(String.class);
                String postalCode = dataSnapshot.child(tag+"").child("address").getValue(String.class);
                String age = dataSnapshot.child(tag+"").child("age").getValue(String.class);
                String qualifications = "\n\nQualifications:\n"+ dataSnapshot.child(tag+"").child("qualifications").getValue(String.class);
                String bio = "\n\nBio:\n"+ dataSnapshot.child(tag+"").child("bio").getValue(String.class);
                String ratings = dataSnapshot.child(tag+"").child("ratings").getValue(String.class);

                TextView n = findViewById(R.id.name);
                n.setText(name);

                TextView a = findViewById(R.id.age);
                a.setText(age+ " yrs old");

                TextView r = findViewById(R.id.address);
                r.setText(ratings+" Stars");

                TextView pc = findViewById(R.id.child);
                pc.setText(postalCode.substring(postalCode.indexOf(", ")+1));

                TextView b = findViewById(R.id.bio);
                b.setText(bio);

                TextView q = findViewById(R.id.job);
                q.setText(qualifications);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //when user clicks the search icon in tool bar, screen changes to search screen
    public void Search(View view) {
        Intent intent = new Intent(this, BabysitterSearch.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }

    //when user clicks the profile icon in tool bar, screen changes to profile screen
    public void Profile(View view) {
        Intent intent = new Intent(this, BabysitterProfile.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }

    //when user clicks the home icon in tool bar, screen changes to home screen
    public void Home(View view) {
        Intent intent = new Intent(this, BabysitterHome.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }

    //when user clicks the edit at the top right, screen changes to profile edit screen
    public void Edit(View view) {
        Intent intent = new Intent(this, BabysitterProfileEdit.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
