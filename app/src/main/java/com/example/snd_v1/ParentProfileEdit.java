package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentProfileEdit extends AppCompatActivity {
    Parent parent = new Parent();
    EditText name,addr,bio;
    int h=0;
    DatePicker age;
    //age, addr, bio;
    //children;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users/Parent");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile_edit);

        final int id = (getIntent().getExtras().getInt("id"));
        h=id;

        name = findViewById(R.id.name);
        //age = findViewById(R.id.age);
        addr = findViewById(R.id.address);
        bio = findViewById(R.id.bio);
        //children = findViewById(R.id.child);

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


    public void setText(){
        name.setText(parent.getName());
        //age.setText("34 yrs old");
        addr.setText(parent.getAddress());
        bio.setText("Bio\n\n"+parent.getBio());
        //children.setText("Child\n\n"+parent.getChild());
        Toast.makeText(getApplicationContext(),parent.getAge(),Toast.LENGTH_SHORT).show();
    }
    public void Search(View view) {
        Intent intent = new Intent(this, ParentSearch.class);
        startActivity(intent);
    }
    public void Profile(View view) {
        Intent intent = new Intent(this, ParentProfile.class);
        startActivity(intent);
    }
    public void JobPost(View view) {
        Intent intent = new Intent(this, ParentPostJob.class);
        startActivity(intent);
    }
    public void Home(View view) {
        Intent intent = new Intent(this, ParentHome.class);
        startActivity(intent);
    }

    public void Save(View view) {
        Intent intent = new Intent(this, ParentProfile.class);

        parent.setName(name.getText().toString());
        parent.setAddress(addr.getText().toString());
        parent.setBio(bio.getText().toString());

        myRef.child(h+"").setValue(parent);

        startActivity(intent);
    }
}
