package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BabysitterProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_profile);
        final int tag = (getIntent().getExtras().getInt("id"));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users/Babysitter");
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
                Toast.makeText(getApplicationContext(),"You are viewing " + name+"'s Profile",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void Search(View view) {
        Intent intent = new Intent(this, BabysitterSearch.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void Profile(View view) {
        Intent intent = new Intent(this, BabysitterProfile.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void Home(View view) {
        Intent intent = new Intent(this, BabysitterHome.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void Edit(View view) {
        Intent intent = new Intent(this, BabysitterProfileEdit.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
