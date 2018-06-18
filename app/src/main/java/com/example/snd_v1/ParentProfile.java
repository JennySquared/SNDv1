package com.example.snd_v1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.Registry;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.module.AppGlideModule;
//import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

public class ParentProfile extends AppCompatActivity {

    Parent parent = new Parent();
    TextView name, age, addr, bio,children;
    ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);

        final int id = (getIntent().getExtras().getInt("id"));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/Parent");

        final ImageView profileImageView = findViewById(R.id.pic);
//        try {
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//            StorageReference pp = storageReference.child(id + "p.jpg");
//            Glide.with(this.getApplicationContext()).load("gs://sitternextdoor-a9719.appspot.com/1b.JPG").into(profileImageView);
//        }
//        catch(Exception e){
//
//        }

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        addr = findViewById(R.id.address);
        bio = findViewById(R.id.bio);
        children = findViewById(R.id.job);
       // pic = findViewById(R.id.pic);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                parent = dataSnapshot.child(id+"").getValue(Parent.class);
//                setText();
                String name = dataSnapshot.child(id+"").child("name").getValue(String.class);
                String age = dataSnapshot.child(id+"").child("age").getValue(String.class)+ " yrs old";
                String addr =dataSnapshot.child(id+"").child("address").getValue(String.class);
                String bio =dataSnapshot.child(id+"").child("bio").getValue(String.class);
                String children =dataSnapshot.child(id+"").child("child").getValue(String.class);
                setText(name, age, addr, bio, children);
                if(id==1) {
                    profileImageView.setImageResource(R.drawable.onep);
                }
                if(id==2){
                    profileImageView.setImageResource(R.drawable.twop);
                }
                if(id==3){
                    profileImageView.setImageResource(R.drawable.threep);
                }
                if(id==2){
                    profileImageView.setImageResource(R.drawable.fourb);
                }
                if(id==3){
                    profileImageView.setImageResource(R.drawable.fiveb);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setText(String n, String a, String add, String b, String c){
//        name.setText(parent.getName());
//        age.setText("34 yrs old");
//        addr.setText(parent.getAddress());
//        bio.setText("Bio\n\n"+parent.getBio());
//        children.setText("Child\n\n"+parent.getChild());
//        Toast.makeText(getApplicationContext(),parent.getAge(),Toast.LENGTH_SHORT).show();

        name.setText(n);
        age.setText(a);
        addr.setText(add);
        bio.setText(b);
        children.setText("Child\n\n"+c);
        Toast.makeText(getApplicationContext(),parent.getAge(),Toast.LENGTH_SHORT).show();


    }

    public void Search(View view) {
        Intent intent = new Intent(this, ParentSearch.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }
    public void Profile(View view) {
        Intent intent = new Intent(this, ParentProfile.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }
    public void JobPost(View view) {
        Intent intent = new Intent(this, ParentPostJob.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }
    public void Home(View view) {
        Intent intent = new Intent(this, ParentHome.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }
    public void Edit(View view) {
        Intent intent = new Intent(this, ParentProfileEdit.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }


}
