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

public class BabysitterSearch extends AppCompatActivity {
    public EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_search);
        search = findViewById(R.id.search);
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

    public void searchButton(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        final String text =search.getText().toString();
        final ArrayList<Integer> counter = new ArrayList<Integer>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(getApplicationContext(),"Successfully Logged In",Toast.LENGTH_SHORT).show();
                try {
                    for (int i = 0; i < (int) dataSnapshot.child("Parent").getChildrenCount(); i++) {
                        if ((dataSnapshot.child("Parent").child(i + 1 + "").child("name").getValue(String.class)).contains(text)) {
                            counter.add(i + 1);
                        } else if ((dataSnapshot.child("Parent").child(i + 1 + "").child("age").getValue(String.class)).contains(text)) {
                            counter.add(i + 1);
                        } else if ((dataSnapshot.child("Parent").child(i + 1 + "").child("bio").getValue(String.class)).contains(text)) {
                            counter.add(i + 1);
                        } else if ((dataSnapshot.child("Parent").child(i + 1 + "").child("child").getValue(String.class)).contains(text)) {
                            counter.add(i + 1);
                        }
//                            else if(text.contains(dataSnapshot.child("Babysitter").child(i+1+"").child("gender").getValue(String.class))){
//                                counter.add(i+1);
                        //                           }
                        else if ((dataSnapshot.child("Parent").child(i + 1 + "").child("address").getValue(String.class)).contains(text)) {
                            counter.add(i + 1);
                        }
                    }
                    if (counter.size() == 0) {
                        Toast.makeText(getApplicationContext(), "Sorry no results found., Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(getApplicationContext(), "" + counter.size(), Toast.LENGTH_LONG).show();

                    ListView list = (ListView) findViewById(R.id.list);
                    String[] name = new String[counter.size()];
                    String[] description = new String[counter.size()];
                    Integer[] imgid = new Integer[counter.size()];
                    String[] address = new String[counter.size()];
                    String[] rating = new String[counter.size()];

                    for (int i = 0; i < counter.size(); i++) {
                        name[i] = dataSnapshot.child("Parent").child(counter.get(i) + "").child("name").getValue(String.class);
                        description[i] = dataSnapshot.child("Parent").child(counter.get(i) + "").child("bio").getValue(String.class);
                        imgid[i] = R.drawable.logo;
                        address[i] = dataSnapshot.child("Parent").child(counter.get(i) + "").child("address").getValue(String.class);
                        rating[i] = dataSnapshot.child("Parent").child(counter.get(i) + "").child("child").getValue(String.class) + "!!!";
                    }
                    listView(name, description, imgid, address, rating, list);
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Sorry no results found. e", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void listView(String[] n, String[] de, Integer[] i,String[] a,String[] r, ListView list ){
        final ParentHomeListView liview = new ParentHomeListView(this, n,de,i,a,r);
        for(int j=0; j<n.length;j++) {
            liview.setName(n[j], j);
            liview.setDescription(de[j], j);
            liview.setAddress(a[j], j);
            liview.setRating(r[j], j);
        }
        list.setAdapter(liview);
    }
}
