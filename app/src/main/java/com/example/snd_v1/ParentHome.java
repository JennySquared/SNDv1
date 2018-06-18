package com.example.snd_v1;

import android.content.ComponentCallbacks;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*
   Title: Parent Home
   Author: Jenny Shen
   Date: March 20, 2018
   Description: Home Screen when Parent first logs in, displays the listview of babysitter profiles from highest rating
*/


public class ParentHome extends AppCompatActivity {

    ProfileListView liview; //custom list view
    Integer[][] rateNum;//2d array that is used to sort the ratings

    //main method
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final ListView list; //listview

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);// set layout

        FirebaseDatabase database = FirebaseDatabase.getInstance();//initialize database

        int numBB = Integer.parseInt(getIntent().getExtras().getString("n"));//get number of babysitters from previous screen
        final int num =numBB;

        //arrays that store babysitter's information
        final String[] name = new String[num];
        final String[] description= new String[num];
        final Integer[] imgid=new Integer[num];
        for(int i = 0;i<num;i++){
            imgid[i]=R.drawable.logo; // default image
        }
        final String[] rating = new String[num];
        final String[] address= new String[num];
        final int[] BabysitterId = new int[num];


        list = (ListView) findViewById(R.id.list);//initailize list view from xml

        //get user id from previous screen
        final int id = (getIntent().getExtras().getInt("id"));

        final DatabaseReference Ref = database.getReference("Users"); //initialize reference

        //retrieve database values
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //if parent's job is booked, toast pops up saying who took the parent's job and resets job to default values
                if(dataSnapshot.child("Parent").child(""+id).child("job").child("info").getValue(String.class).compareTo("0")!=0&&dataSnapshot.child("Parent").child(""+id).child("job").child("date").getValue(String.class).equals("0")){
                    Toast.makeText(getApplicationContext(),"Your booking is booked by "
                            +dataSnapshot.child("Babysitter").child(""+Integer.parseInt(dataSnapshot.child("Parent").
                            child(""+id).child("job").child("info").getValue(String.class))).child("name").
                            getValue(String.class),Toast.LENGTH_SHORT).show();
                    Ref.child("Parent").child("id").child("job").child("date").setValue("0");
                    Ref.child("Parent").child("id").child("job").child("start").setValue("0");
                    Ref.child("Parent").child("id").child("job").child("end").setValue("0");
                    Ref.child("Parent").child("id").child("job").child("info").setValue("0");

                }

                //set values from rateNum (first column is the ratings, second column is babysitter id)
                rateNum=new Integer[num][2];
                for(int i=0; i<num;i++) {
                    rateNum[i][0]=Integer.parseInt(dataSnapshot.child("Babysitter").child(i+1+"").child("ratings").getValue(String.class));
                    rateNum[i][1]=i+1;

                }

                //sorts first column from highest to lowest
                Arrays.sort(rateNum, new Comparator<Integer[]>() {
                    @Override
                    //arguments to this method represent the arrays to be sorted
                    public int compare(Integer[] o1, Integer[] o2) {
                        //get the item ids which are at index 0 of the array
                        Integer itemIdOne = o1[0];
                        Integer itemIdTwo = o2[0];
                        // sort on item id
                        return itemIdTwo.compareTo(itemIdOne);
                    }
                });
                for(int i=0; i<num;i++) {
                    BabysitterId[i]= rateNum[i][0]+1;
                }

                //sets ListView
                setListView(name, description, imgid, address, rating, BabysitterId);

                //mutates information in the listview to modify formatting
                for(int i=0; i<num;i++) {
                    liview.setName(dataSnapshot.child("Babysitter").child(rateNum[i][1]+"").child("name").getValue(String.class), i);
                    liview.setDescription(dataSnapshot.child("Babysitter").child(rateNum[i][1]+"").child("bio").getValue(String.class), i);
                    liview.setAddress(dataSnapshot.child("Babysitter").child(rateNum[i][1]+"").child("address").getValue(String.class), i);
                    liview.setRating(dataSnapshot.child("Babysitter").child(rateNum[i][1]+"").child("ratings").getValue(String.class), i);
                }
                list.setAdapter(liview);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

     //if parent clicks an element of the listview, the screen changes to parent views babysitter profile screen with the parent id and babysitter id
     list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int tag=rateNum[position][1];
                Intent intent = new Intent(ParentHome.this, ParentViewBProfile.class);
                intent.putExtra("p", tag);
                int id = (getIntent().getExtras().getInt("id"));
                intent.putExtra("id",id);
                String numBB = (getIntent().getExtras().getString("n"));
                intent.putExtra("n",numBB);
                startActivity(intent);
            }
     });

    }

    //intializes custom listview
    public void setListView(String[]name, String[] description, Integer[] imgid, String[] address, String[] rating, int[] BabysitterId) {
         liview = new ProfileListView(this, name,description,imgid,address,rating, BabysitterId);

    }

    //when user clicks the search icon in tool bar, screen changes to search screen
    public void Search(View view) {
        Intent intent = new Intent(this, ParentSearch.class);
        int id = (getIntent().getExtras().getInt("id"));
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    //when user clicks the profile icon in tool bar, screen changes to profile screen
    public void Profile(View view) {
        Intent intent = new Intent(this, ParentProfile.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }

    //when user clicks the job post icon in tool bar, screen changes to job post screen
    public void JobPost(View view) {
        Intent intent = new Intent(this, ParentPostJob.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }

    //when user clicks the home icon in tool bar, screen changes to home screen
    public void Home(View view) {
        Intent intent = new Intent(this, ParentHome.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }

    //when user clicks the logout button, screen changes to login screen
    public void Logout(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }


}

