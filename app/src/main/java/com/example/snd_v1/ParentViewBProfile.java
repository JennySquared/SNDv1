package com.example.snd_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
   Title: Parent View Babysitter Profile
   Author: Jenny Shen
   Date: April 15, 2018
   Description: Screen for Parent to View Babysitter's information
*/

public class ParentViewBProfile extends AppCompatActivity {

    //main method
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();//initialize database
        final DatabaseReference myRef = database.getReference("Users/Babysitter");//initialize reference

        final int tag = (getIntent().getExtras().getInt("p"));//babysitter id

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view_bprofile);//set layour

        //set image from Firebase
        final ImageView profileImageView = findViewById(R.id.imageView2);
        try {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference bb = storageReference.child(tag+"b.jpg");
            Glide.with(this.getApplicationContext()).load(bb).into(profileImageView);
        }
        catch(Exception e){

        }

        //retrieval of babysitter profile information and sets it to text views
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieval of values
                String name = dataSnapshot.child(tag+"").child("name").getValue(String.class);
                String postalCode = dataSnapshot.child(tag+"").child("address").getValue(String.class);
                String age = dataSnapshot.child(tag+"").child("age").getValue(String.class);
                String qualifications = "\n\nQualifications:\n"+ dataSnapshot.child(tag+"").child("qualifications").getValue(String.class);
                String bio = "\n\nBio:\n"+ dataSnapshot.child(tag+"").child("bio").getValue(String.class);
                String ratings = dataSnapshot.child(tag+"").child("ratings").getValue(String.class);

                //setting strings to its textviews
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

                Toast.makeText(getApplicationContext(),"You are viewing " + name+"'s Profile",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //when user clicks the search icon in tool bar,screen changes to search screen
    public void Search(View view) {
        Intent intent = new Intent(this, ParentSearch.class);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }

    //when user clicks the profile icon in tool bar,screen changes to profile screen
    public void Profile(View view) {
        Intent intent = new Intent(this, ParentProfile.class);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }

    //when user clicks the job post icon in tool bar,screen changes to job post screen
    public void JobPost(View view) {
        Intent intent = new Intent(this, ParentPostJob.class);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }

    //when user clicks the home icon in tool bar,screen changes to home screen
    public void Home(View view) {
        Intent intent = new Intent(this, ParentHome.class);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }

}
