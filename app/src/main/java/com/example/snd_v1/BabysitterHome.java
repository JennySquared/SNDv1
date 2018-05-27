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

public class BabysitterHome extends AppCompatActivity {
    public static int tag =-1;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> tStart = new ArrayList<>();
    ArrayList<Integer> imgid = new ArrayList<>();
    ArrayList<String> tEnd = new ArrayList<>();
    ArrayList<String> addInfo = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_home);

        final ListView list= (ListView)findViewById(R.id.l);;

        FirebaseDatabase database = FirebaseDatabase.getInstance();

//        final BabysitterHomeListView liview = new BabysitterHomeListView(this, name,date,imgid,tStart,tEnd);
//        list.setAdapter(liview);

        DatabaseReference Ref = database.getReference();

        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(),(getIntent().getExtras().getInt("id"))+"",Toast.LENGTH_SHORT).show();

                int num = Integer.parseInt(dataSnapshot.child("numParent").getValue(String.class));
                for(int i = 0; i<num; i++){
                    //int numJobs =dataSnapshot.child("Users").child("Parent").child(i+1+"").child("jobs").getValue(ArrayList.class).size();
                    jobPost job = dataSnapshot.child("Users").child("Parent").child(i+1+"").child("job").getValue(jobPost.class);

//                        for(int j = 0; j<10;j++){
//                            date.add(jobs[j].getDate());
//                            tStart.add(jobs[j].getStart());
//                            tEnd.add(jobs[j].getEnd());
//                            name.add(dataSnapshot.child("Users").child("Parent").child(i+1+"").child("Name").getValue(String.class));
//                            imgid.add(R.drawable.logo);
//
//                            if(jobs[j+1]==null){
//                                break;
//                            }
//                        }
                    if(job.getEnd().equals("0")){

                    }
                    else{
                        date.add(job.getDate());
                        tStart.add("Time:    "+ job.getStart() + "    to   ");
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

                //Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(BabysitterHome.this, BabysitterViewPProfile.class);
                intent.putExtra("p", position+1);
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
        startActivity(intent);
    }
    public void Profile(View view) {
        Intent intent = new Intent(this, BabysitterProfile.class);
        startActivity(intent);
    }

    public void Home(View view) {
        Intent intent = new Intent(this, BabysitterHome.class);
        startActivity(intent);
    }

    public void Logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
