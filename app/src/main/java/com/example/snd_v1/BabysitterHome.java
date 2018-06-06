package com.example.snd_v1;

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

import java.util.ArrayList;

/*
   Title: Babysitter Home
   Author: Jenny Shen
   Date: March 20, 2018
   Description: Home Screen when Babysitter first logs in, displays the listview of parent jobs
*/
public class BabysitterHome extends AppCompatActivity {

    public static int tag =-1;//stores Babysitter id

    //Arraylist stores an attribute of every job post
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> tStart = new ArrayList<>();
    ArrayList<Integer> imgid = new ArrayList<>();
    ArrayList<String> tEnd = new ArrayList<>();

    //Main Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_home);

        final ListView list= (ListView)findViewById(R.id.l);//listview in the GUI

        FirebaseDatabase database = FirebaseDatabase.getInstance();//initialize database
        DatabaseReference Ref = database.getReference();//retrieve database reference

        //Action Listener for retrieving data from the database
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(),(getIntent().getExtras().getInt("id"))+"",Toast.LENGTH_SHORT).show();

                int num = Integer.parseInt(dataSnapshot.child("numParents").getValue(String.class));//number of parents in the system

                for(int i = 0; i<num; i++){
                    jobPost job = dataSnapshot.child("Users").child("Parent").child(i+1+"").child("job").getValue(jobPost.class);

                    if(job.getEnd().equals("0")){

                    }
                    else{
                        date.add(job.getDate());
                        tStart.add("Time:    "+ job.getStart() + " to   ");
                        tEnd.add(job.getEnd());
                        name.add(dataSnapshot.child("Users").child("Parent").child(i+1+"").child("name").getValue(String.class));
                        imgid.add(R.drawable.logo);
                    }

                }
                num = date.size();
                String [] d = new String[num];
                String [] s = new String[num];
                String [] e = new String[num];
                String [] n = new String[num];
                Integer [] i = new Integer[num];
                for (int j =0; j < num; j++){
                    d[j] = date.get(j);
                    s[j] = tStart.get(j);
                    e[j] = tEnd.get(j);
                    n[j] = name.get(j);
                    i[j] = imgid.get(j);
                }

                setListView(n,d,i,e,s,list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                tag=position;

                Intent intent = new Intent(BabysitterHome.this, BabysitterViewPProfile.class);
                intent.putExtra("p", position+1);
                int id = (getIntent().getExtras().getInt("id"));
                intent.putExtra("id",id);
                startActivity(intent);


            }
        });

    }
    public void setListView(String[] n,String[] d,Integer[] i,String[] s,String[] e, ListView list){
        BabysitterHomeListView li = new BabysitterHomeListView(this, n,d,i,s,e);
        list.setAdapter(li);
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

    public void Logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
