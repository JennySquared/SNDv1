package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*
   Title: Babysitter Home
   Author: Jenny Shen
   Date: March 20, 2018
   Description: Home Screen when Babysitter first logs in, displays the listview of parent jobs from most recent
*/
public class BabysitterHome extends AppCompatActivity {

    public static int tag =-1;//stores Babysitter id

    //ArrayList stores an attribute of every job post
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> tStart = new ArrayList<>();
    ArrayList<Integer> imgid = new ArrayList<>();
    ArrayList<Integer> dateNum = new ArrayList<>();
    ArrayList<Integer> id = new ArrayList<>();
    ArrayList<String> tEnd = new ArrayList<>();
    Integer[][] dn;

    //Main Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_home);//set layout to babysitter home xml
        FirebaseApp.initializeApp(getApplicationContext());//Firebase Storage App


        final ListView list= (ListView)findViewById(R.id.l);//listview in the GUI

        FirebaseDatabase database = FirebaseDatabase.getInstance();//initialize database
        DatabaseReference Ref = database.getReference();//retrieve database reference

        //Action Listener for retrieving data from the database
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int num = Integer.parseInt(dataSnapshot.child("numParents").getValue(String.class));//number of parents in the system

                for(int i = 0; i<num; i++){
                    JobPost job = dataSnapshot.child("Users").child("Parent").child(i+1+"").child("job").getValue(JobPost.class);//retrieve job post from parent

                    //if empty, don't add it to the listview
                    if(job.getEnd().equals("0")){

                    }
                    else{
                        //get date, remove / and parse the string into an integer to sort the dates later on
                        String month = (job.getDate().substring(0,job.getDate().indexOf("/")));
                        String day = (job.getDate().substring(job.getDate().indexOf("/")+1,job.getDate().lastIndexOf("/") ));
                        String year =(job.getDate().substring(job.getDate().lastIndexOf("/")+1 ));
                        int d = Integer.parseInt(year+month+day);
                        dateNum.add(d);

                        //information of an element of a listview
                        date.add(job.getDate()); //date information
                        tStart.add("Time:    "+ job.getStart() + " to   "); //start time
                        tEnd.add(job.getEnd());//end time
                        id.add(i);//parent name
                        name.add(dataSnapshot.child("Users").child("Parent").child(i+1+"").child("name").getValue(String.class));//name of the parent
                    }

                }
                //2D array for sorting first column is the date, second is the parent id
                num = date.size();
                dn = new Integer[num][2];
                for (int j =0; j < num; j++){
                    dn[j][0]= dateNum.get(j);
                    dn[j][1]= j;
                }
                //sorting the date column in the 2D array
                Arrays.sort(dn, new Comparator<Integer[]>() {
                    @Override
                    //arguments to this method represent the arrays to be sorted
                    public int compare(Integer[] o1, Integer[] o2) {
                        //get the item ids which are at index 0 of the array
                        Integer itemIdOne = o1[0];
                        Integer itemIdTwo = o2[0];
                        // sort on item id
                        return itemIdOne.compareTo(itemIdTwo);
                    }
                });

                //once sorted, store the elements from the arraylist to the sorted array
                String [] d = new String[num];
                String [] s = new String[num];
                String [] e = new String[num];
                String [] n = new String[num];
                Integer [] i = new Integer[num];
                int[] parentId = new int[num];
                for (int j =0; j < num; j++){
                    d[j] = date.get(dn[j][1]);
                    s[j] = tStart.get(dn[j][1]);
                    e[j] = tEnd.get(dn[j][1]);
                    n[j] = name.get(dn[j][1]);
                    i[j] = imgid.get(dn[j][1]);
                    parentId[j]= dn[j][1];

                }

                //call ListView method
                setListView(n,d,i,e,s,parentId,list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //when the user clicks on a job post, changes to profile screen of the parent
        //while sending the babysitter id and parent id that was cicked
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(BabysitterHome.this, BabysitterViewPProfile.class);
                tag=(dn[position][1])+1; //parent id
                intent.putExtra("p", tag);
                int id = (getIntent().getExtras().getInt("id"));//babysitter id
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

    }

    //intialize listview with arrays of names, dates, images, start time and end time, along with the list widget itself
    public void setListView(String[] n,String[] d,Integer[] i,String[] s,String[] e,int[] id, ListView list){
        JobPostingsListView li = new JobPostingsListView(this, n,d,i,s,e,id);
        list.setAdapter(li);
    }

    //when user clicks the search icon in tool bar, screen changes to search screen
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

    //when user clicks the profile icon in tool bar,screen changes to the login screen
    public void Logout(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
