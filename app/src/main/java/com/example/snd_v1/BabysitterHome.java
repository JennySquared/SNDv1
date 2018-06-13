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
import java.util.Arrays;
import java.util.Comparator;

/*
   Title: Babysitter Home
   Author: Jenny Shen
   Date: March 20, 2018
   Description: Home Screen when Babysitter first logs in, displays the listview of parent jobs
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
                        String day = (job.getDate().substring(0,job.getDate().indexOf("/")));
                        String month = (job.getDate().substring(job.getDate().indexOf("/")+1,job.getDate().lastIndexOf("/") ));
                        String year =(job.getDate().substring(job.getDate().lastIndexOf("/")+1 ));
                        int d = Integer.parseInt(year+day+month);
                        dateNum.add(d);
                        tStart.add("Time:    "+ job.getStart() + " to   ");
                        tEnd.add(job.getEnd());
                        id.add(i);
                        name.add(dataSnapshot.child("Users").child("Parent").child(i+1+"").child("name").getValue(String.class));
                        if(i==0) {
                            imgid.add(R.drawable.onep);
                        }
                        if(i==1){
                            imgid.add(R.drawable.twop);
                        }
                        if(i==2){
                            imgid.add(R.drawable.threep);
                        }
                        if(i==3){
                            imgid.add(R.drawable.fourb);
                        }
                        if(i==4){
                            imgid.add(R.drawable.fiveb);
                        }
                    }

                }
                num = date.size();
                dn = new Integer[num][2];
                for (int j =0; j < num; j++){
                    dn[j][0]= dateNum.get(j);
                    dn[j][1]= j;
                }
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
                String [] d = new String[num];
                String [] s = new String[num];
                String [] e = new String[num];
                String [] n = new String[num];
                Integer [] i = new Integer[num];
                for (int j =0; j < num; j++){
                    d[j] = date.get(dn[j][1]);
                    s[j] = tStart.get(dn[j][1]);
                    e[j] = tEnd.get(dn[j][1]);
                    n[j] = name.get(dn[j][1]);
                    i[j] = imgid.get(dn[j][1]);
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
                tag=(dn[position][1])+1;

                Intent intent = new Intent(BabysitterHome.this, BabysitterViewPProfile.class);
                intent.putExtra("p", tag);
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
