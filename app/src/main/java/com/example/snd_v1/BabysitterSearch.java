package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
   Title: Babysitter Search
   Author: Jenny Shen
   Date: April 10, 2018
   Description: Search Screen for a babysitter to search a string, and a list of parents shows up with that contains that string
*/

public class BabysitterSearch extends AppCompatActivity {

    public EditText search; //user enters a search keyword
    ListView list;//profiles of parents search results
    int[] idd;//results' parent ids

    //main method
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_search);//set layout to babysitter search xml

        search = findViewById(R.id.search); //search textfield

    }

    //when user clicks the search icon in tool bar,screen changes to search screen
    public void Search(View view) {
        Intent intent = new Intent(this, BabysitterSearch.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }

    //when user clicks the profile icon in tool bar,screen changes to profile screen
    public void Profile(View view) {
        Intent intent = new Intent(this, BabysitterProfile.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }

    //when user clicks the home icon in tool bar,screen changes to home screen
    public void Home(View view) {
        Intent intent = new Intent(this, BabysitterHome.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }

    //when user presses search icon
    public void searchButton(View view) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();//initialize database
        final DatabaseReference myRef = database.getReference("Users/Parent");//initialize reference

        final String text =search.getText().toString();

        final ArrayList<Integer> counter = new ArrayList<Integer>();//stores ids that has the search string

        //retrieval of Database values
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    //checks if each value in the parent's profile matches the search string
                    //if so, the id of the parent is added into the arraylist
                    for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                        if ((dataSnapshot.child(i + 1 + "").child("name").getValue(String.class)).contains(text)) {
                            counter.add(i + 1);
                        }
                        else if ((dataSnapshot.child(i + 1 + "").child("age").getValue(String.class)).contains(text)) {
                            counter.add(i + 1);
                        } else if ((dataSnapshot.child(i + 1 + "").child("bio").getValue(String.class)).contains(text)) {
                            counter.add(i + 1);
                        } else if ((dataSnapshot.child(i + 1 + "").child("child").getValue(String.class)).contains(text)) {
                            counter.add(i + 1);
                        }
                        else if ((dataSnapshot.child(i + 1 + "").child("address").getValue(String.class)).contains(text)) {
                            counter.add(i + 1);
                        }
                    }
                    //if no results
                    if (counter.size() == 0) {
                        Toast.makeText(getApplicationContext(), "Sorry no results found.", Toast.LENGTH_LONG).show();
                    }

                     list = (ListView) findViewById(R.id.list);//initialize the listView

                    //parent's information that does have the search string
                    String[] name = new String[counter.size()];
                    String[] description = new String[counter.size()];
                    Integer[] imgid = new Integer[counter.size()];
                    String[] address = new String[counter.size()];
                    String[] rating = new String[counter.size()];
                    idd = new int[counter.size()];

                    //gets the parent's information from database
                    for (int i = 0; i < counter.size(); i++) {
                        name[i] = dataSnapshot.child(counter.get(i) + "").child("name").getValue(String.class);
                        description[i] = dataSnapshot.child(counter.get(i) + "").child("bio").getValue(String.class);
                        imgid[i] = R.drawable.home;
                        address[i] = dataSnapshot.child(counter.get(i) + "").child("address").getValue(String.class);
                        rating[i] = dataSnapshot.child(counter.get(i) + "").child("child").getValue(String.class) + "!!!";
                        idd[i] = counter.get(i);
                    }

                    //sets listview with parent's information
                    listView(name, description, imgid, address, rating, list );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    //method that sets information into the listview
    public void listView(String[] n, String[] de, Integer[] i,String[] a,String[] r,  ListView list ){

        final ProfileListView liview = new ProfileListView(this, n,de,i,a,r,idd);//intializes listview

        //mutates information in the listview to improve formatting
        for(int j=0; j<n.length;j++) {
            liview.setName(n[j], j);
            liview.setDescription(de[j], j);
            liview.setAddress(a[j], j);
            liview.setRating(r[j], j);
        }
        list.setAdapter(liview);

        //if user presses on one of the listview elements
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //pressed parent's id
                int tag=(idd[position]);

                //switch screen to babysitter view parent's profile with parent's id and the user id
                Intent intent = new Intent(BabysitterSearch.this, BabysitterViewPProfile.class);
                intent.putExtra("p", tag);
                int id = (getIntent().getExtras().getInt("id"));
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
}
