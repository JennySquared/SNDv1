package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ParentHome extends AppCompatActivity {
    public static int tag =-1;


//    DataSnapshot dataSnap;
//    Babysitter n = dataSnap.child("Users").child("Babysitter").child("1").getValue(Babysitter.class);
//    String[] name;
//    String[] description;
//    Integer[] imgid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        final ListView list;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("numBabysitters");
//
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                numBB = dataSnapshot.getValue(String.class);
////                num=Integer.parseInt(numBB);
//                Toast.makeText(getApplicationContext(),"Read",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        String numBB = (getIntent().getExtras().getString("n"));
        final int num = Integer.parseInt(numBB);
        String[] name = new String[num];
        String[] description= new String[num];
        Integer[] imgid={R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo};
        String[] rating = new String[num];
        String[] address= new String[num];

        list = (ListView) findViewById(R.id.listView);
        final ParentHomeListView liview = new ParentHomeListView(this, name,description,imgid,address,rating);
        list.setAdapter(liview);

        DatabaseReference Ref = database.getReference("Users");

        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(),(getIntent().getExtras().getInt("id"))+"",Toast.LENGTH_SHORT).show();


                for(int i=0; i<(int)dataSnapshot.child("Babysitter").getChildrenCount();i++) {
                    liview.setName(dataSnapshot.child("Babysitter").child(i+1+"").child("name").getValue(String.class), i);
                    liview.setDescription(dataSnapshot.child("Babysitter").child(i+1+"").child("bio").getValue(String.class), i);
                    liview.setAddress(dataSnapshot.child("Babysitter").child(i+1+"").child("address").getValue(String.class), i);
                    liview.setRating(dataSnapshot.child("Babysitter").child(i+1+"").child("rating").getValue(String.class), i);
                }
                list.setAdapter(liview);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

     list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                tag=position;

                //Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ParentHome.this, ParentViewBProfile.class);
                intent.putExtra("p", position+1);
                startActivity(intent);


            }
     });

    }

//    public void setNum(int n){
//        num = n;
//    }

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
//    public void BBprofile(View view) {
//        Intent intent = new Intent(this, ParentViewBProfile.class);
////         //tag = list.getId();
//        list.getPositionForView(view);
//        startActivity(intent);
//
//    }

    public void Logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}

