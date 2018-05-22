package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentPostJob extends AppCompatActivity {
    Parent p = new Parent();
    String d, s,e,i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_post_job);
    }

    public void Submit(View view) {
        Intent intent = new Intent(this, ParentPostJob.class);
        final int id = (getIntent().getExtras().getInt("id"));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/Parent");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                p=dataSnapshot.child(id+"").getValue(Parent.class);
                p.createJob(d,s,e,i);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.child(id+"").setValue(p);

        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void Search(View view) {
        Intent intent = new Intent(this, ParentSearch.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void Profile(View view) {
        Intent intent = new Intent(this, ParentProfile.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void JobPost(View view) {
        Intent intent = new Intent(this, ParentPostJob.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void Home(View view) {
        Intent intent = new Intent(this, ParentHome.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
