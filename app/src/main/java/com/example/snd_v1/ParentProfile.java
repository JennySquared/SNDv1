package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentProfile extends AppCompatActivity {

    Parent parent = new Parent();
    TextView name, age, addr, bio,children;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);

        final int id = (getIntent().getExtras().getInt("id"));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/Parent");

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        addr = findViewById(R.id.address);
        bio = findViewById(R.id.bio);
        children = findViewById(R.id.child);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
    public void setText(){
        name.setText(parent.getName());
        age.setText(parent.getAge());
        addr.setText(parent.getAddress());
        bio.setText("Bio\n\n"+parent.getBio());
        children.setText("Child\n\n"+parent.getChild());
        Toast.makeText(getApplicationContext(),parent.getAge(),Toast.LENGTH_SHORT).show();
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
    public void Edit(View view) {
        Intent intent = new Intent(this, ParentProfileEdit.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
