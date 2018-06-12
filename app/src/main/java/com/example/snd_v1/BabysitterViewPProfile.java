package com.example.snd_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

public class BabysitterViewPProfile extends AppCompatActivity {
    int tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_view_pprofile);

        final ImageView profileImageView = findViewById(R.id.imageView2);
        final int t = (getIntent().getExtras().getInt("p"));
//        try {
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//            StorageReference pp = storageReference.child(t+"p.jpg");
//            Glide.with(this.getApplicationContext()).load(pp).into(profileImageView);
//        }
//        catch(Exception e){
//
//        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users/Parent");

        tag=t;
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child(tag+"").child("name").getValue(String.class);
                TextView n = findViewById(R.id.name);
                n.setText(name);

                String age = dataSnapshot.child(tag+"").child("age").getValue(String.class)+" yrs old";
                TextView a = findViewById(R.id.age);
                a.setText(age);

                String address = dataSnapshot.child(tag+"").child("address").getValue(String.class);
                TextView add = findViewById(R.id.address);
                add.setText(address);

                String child = "Child: " +dataSnapshot.child(tag+"").child("child").getValue(String.class);
                TextView c = findViewById(R.id.child);
                c.setText(child);

                String bio = "Bio:\n"+dataSnapshot.child(tag+"").child("bio").getValue(String.class);
                TextView b = findViewById(R.id.bio);
                b.setText(bio);

                jobPost post = dataSnapshot.child(tag+"").child("job").getValue(jobPost.class);

                if(!post.getDate().equals("0")){
                    String job = "Job Information\n"+post.getDate() +" at "+post.getStart()+ " till "+ post.getEnd()+"\nAdditional Information: "+
                            post.getInfo();
                    TextView j = findViewById(R.id.job);
                    j.setText(job);
                }
                if(tag==1){
                    profileImageView.setImageResource(R.drawable.onep);
                }
                if(tag==2){
                    profileImageView.setImageResource(R.drawable.twop);
                }
                if(tag==3){
                    profileImageView.setImageResource(R.drawable.threep);
                }
                if(tag==4){
                    profileImageView.setImageResource(R.drawable.fourb);
                }
                if(tag==5){
                    profileImageView.setImageResource(R.drawable.fiveb);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        name.setText(tag+"");
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

    public void Request(View view) {
        Intent intent = new Intent(this, BabysitterHome.class);
        int id = (getIntent().getExtras().getInt("id"));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/Parent");
        myRef.child(1+"").child("job").child("date").setValue("0");
        myRef.child(1+"").child("job").child("start").setValue("0");
        myRef.child(1+"").child("job").child("end").setValue("0");
        myRef.child(1+"").child("job").child("info").setValue(""+id);
        Toast.makeText(getApplicationContext(),"You are booked!",Toast.LENGTH_SHORT).show();

        intent.putExtra("id",id);
        startActivity(intent);
    }


}
