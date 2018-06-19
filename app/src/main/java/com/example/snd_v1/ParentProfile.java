package com.example.snd_v1;

import android.content.Context;
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

import java.io.InputStream;

/*
Title: Parent Profile
Author: Jenny Shen
Date: April 13, 2018
Description: Parent can view their information and what their profile looks like
 */

public class ParentProfile extends AppCompatActivity {

    //user profile object
    Parent parent = new Parent();

    //textviews for profile information
    TextView name, age, addr, bio,children;

    //main method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);//set layout

        //get user id
        final int id = (getIntent().getExtras().getInt("id"));

        //intialize database and reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/Parent");

        //retrieve image from firebase storage
        final ImageView profileImageView = findViewById(R.id.pic);
        try {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference pp = storageReference.child(id + "p.jpg");
            Glide.with(this.getApplicationContext()).load(pp).into(profileImageView);
        }
        catch(Exception e){

        }

        //set textviews from xml layout
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        addr = findViewById(R.id.address);
        bio = findViewById(R.id.bio);
        children = findViewById(R.id.job);

        //retrieve user object
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parent = dataSnapshot.child(id+"").getValue(Parent.class);
                setText();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //takes database parent object and sets textviews text based on the user's attributes
    public void setText(){
        name.setText(parent.getName());
        age.setText(parent.getAge());
        addr.setText(parent.getAddress());
        bio.setText("Bio\n\n"+parent.getBio());
        children.setText("Child\n\n"+parent.getChild());
        Toast.makeText(getApplicationContext(),parent.getAge(),Toast.LENGTH_SHORT).show();
    }

    //when user clicks the search icon in tool bar, screen changes to search screen
    public void Search(View view) {
        Intent intent = new Intent(this, ParentSearch.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }

    //when user clicks the profile icon in tool bar, screen changes to profile screen
    public void Profile(View view) {
        Intent intent = new Intent(this, ParentProfile.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }

    //when user clicks the job post icon in tool bar, screen changes to job post screen
    public void JobPost(View view) {
        Intent intent = new Intent(this, ParentPostJob.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }

    //when user clicks the job post icon in tool bar, screen changes to job post screen
    public void Home(View view) {
        Intent intent = new Intent(this, ParentHome.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }

    //when user clicks the edit icon at the top of the screen, screen changes to profile edit screen
    public void Edit(View view) {
        Intent intent = new Intent(this, ParentProfileEdit.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }


}
