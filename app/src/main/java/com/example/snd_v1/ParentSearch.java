package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParentSearch extends AppCompatActivity {
    public EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_search);
        search = findViewById(R.id.Search);
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

    public void searchButton(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");
                final String text =search.getText().toString();
                final ArrayList<Integer> counter = new ArrayList<Integer>();
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Toast.makeText(getApplicationContext(),"Successfully Logged In",Toast.LENGTH_SHORT).show();

                        for(int i=0; i<(int)dataSnapshot.getChildrenCount();i++) {
                            if((dataSnapshot.child("Babysitter").child(i+1+"").child("name").getValue(String.class)).contains(text)){
                                counter.add(i+1);
                            }
                            else if(text.contains(dataSnapshot.child("Babysitter").child(i+1+"").child("age").getValue(String.class))){
                                counter.add(i+1);
                            }
                            else if(text.contains(dataSnapshot.child("Babysitter").child(i+1+"").child("bio").getValue(String.class))){
                                counter.add(i+1);
                            }
                            else if(text.contains(dataSnapshot.child("Babysitter").child(i+1+"").child("experience").getValue(String.class))){
                                counter.add(i+1);
                            }
//                            else if(text.contains(dataSnapshot.child("Babysitter").child(i+1+"").child("gender").getValue(String.class))){
//                                counter.add(i+1);
 //                           }
                            else if(text.contains(dataSnapshot.child("Babysitter").child(i+1+"").child("address").getValue(String.class))){
                                counter.add(i+1);
                            }
                            else if(text.contains(dataSnapshot.child("Babysitter").child(i+1+"").child("qualifications").getValue(String.class))){
                                counter.add(i+1);
                            }
                            else if(text.contains(dataSnapshot.child("Babysitter").child(i+1+"").child("rating").getValue(String.class))){
                                counter.add(i+1);
                            }
                        }
                        if(counter.size()==0){
                            Toast.makeText(getApplicationContext(),"Sorry no results found HAHA SCREW U WHERES MICHEAL????",Toast.LENGTH_LONG).show();
                        }

                        Toast.makeText(getApplicationContext(),""+counter.size(),Toast.LENGTH_LONG).show();

                        ListView list = (ListView) findViewById(R.id.list);
                        String[] name = new String[counter.size()];
                        String[] description = new String[counter.size()];
                        Integer[] imgid = new Integer[counter.size()];
                        String[] address = new String[counter.size()];
                        String[] rating = new String[counter.size()];

                        for(int i=0;i<counter.size();i++){
                            name[i] = dataSnapshot.child("Babysitter").child(counter.get(i)+"").child("name").getValue(String.class);
                            description[i] = dataSnapshot.child("Babysitter").child(counter.get(i)+"").child("bio").getValue(String.class);
                            imgid[i] = R.drawable.logo;
                            address[i] = dataSnapshot.child("Babysitter").child(counter.get(i)+"").child("address").getValue(String.class);
                            rating[i] = dataSnapshot.child("Babysitter").child(counter.get(i)+"").child("rating").getValue(String.class);
                        }
                        listView(name, description,imgid,address,rating,list);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public void listView(String[] n, String[] de, Integer[] i,String[] a,String[] r, ListView list ){
        final ParentHomeListView liview = new ParentHomeListView(this, n,de,i,a,r);
        list.setAdapter(liview);
    }
}
