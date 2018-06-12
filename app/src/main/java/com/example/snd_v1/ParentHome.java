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

import java.util.Arrays;
import java.util.Comparator;

public class ParentHome extends AppCompatActivity {
    public static int tag =-1;
    Integer[][] rateNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        final ListView list;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        int numBB;

        try {
             numBB = Integer.parseInt(getIntent().getExtras().getString("n"));
        }
        catch(Exception e){
             numBB= 5;
        }
        final int num =numBB;


        final String[] name = new String[num];
        final String[] description= new String[num];
        final Integer[] imgid=new Integer[num];
        for(int i = 0;i<num;i++){
            imgid[i]=R.drawable.logo;
        }
        final String[] rating = new String[num];
        final String[] address= new String[num];

        list = (ListView) findViewById(R.id.list);
        final ParentHomeListView liview = new ParentHomeListView(this, name,description,imgid,address,rating);
        list.setAdapter(liview);

        final int id = (getIntent().getExtras().getInt("id"));

        final DatabaseReference Ref = database.getReference("Users");

        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(getApplicationContext(),(getIntent().getExtras().getInt("id"))+"",Toast.LENGTH_SHORT).show();

                if(dataSnapshot.child("Parent").child(""+id).child("job").child("info").getValue(String.class).compareTo("0")!=0&&dataSnapshot.child("Parent").child(""+id).child("job").child("date").getValue(String.class).equals("0")){
                    Toast.makeText(getApplicationContext(),"Your booking is booked by "
                            +dataSnapshot.child("Babysitter").child(""+Integer.parseInt(dataSnapshot.child("Parent").
                            child(""+id).child("job").child("info").getValue(String.class))).child("name").
                            getValue(String.class),Toast.LENGTH_SHORT).show();
                    int id = (getIntent().getExtras().getInt("id"));
                    Ref.child("Parent").child("id").child("job").child("date").setValue("0");
                    Ref.child("Parent").child("id").child("job").child("start").setValue("0");
                    Ref.child("Parent").child("id").child("job").child("end").setValue("0");
                    Ref.child("Parent").child("id").child("job").child("info").setValue("0");

                }
                rateNum=new Integer[num][2];
                for(int i=0; i<num;i++) {
//                    name[i]=dataSnapshot.child("Babysitter").child(i+1+"").child("name").getValue(String.class);
//                    description[i]=dataSnapshot.child("Babysitter").child(i+1+"").child("bio").getValue(String.class);
//                    address[i]=dataSnapshot.child("Babysitter").child(i+1+"").child("address").getValue(String.class);
//                    rating[i]=dataSnapshot.child("Babysitter").child(i+1+"").child("ratings").getValue(String.class);
                    rateNum[i][0]=Integer.parseInt(dataSnapshot.child("Babysitter").child(i+1+"").child("ratings").getValue(String.class));
                    rateNum[i][1]=i+1;

                }
                Arrays.sort(rateNum, new Comparator<Integer[]>() {
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
                for(int i=0; i<num;i++) {
                    int nu =rateNum[i][1];
                    liview.setName(dataSnapshot.child("Babysitter").child(nu+"").child("name").getValue(String.class), rateNum[i][1]-1);
                    liview.setDescription(dataSnapshot.child("Babysitter").child(nu+"").child("bio").getValue(String.class), rateNum[i][1]-1);
                    liview.setAddress(dataSnapshot.child("Babysitter").child(nu+"").child("address").getValue(String.class), rateNum[i][1]-1);
                    liview.setRating(dataSnapshot.child("Babysitter").child(nu+"").child("ratings").getValue(String.class), rateNum[i][1]-1);
                    if(nu==1){
                        liview.setImgid(R.drawable.oneb, i);
                    }
                    if(nu==2){
                        liview.setImgid(R.drawable.twob, i);
                    }
                    if(nu==3){
                        liview.setImgid(R.drawable.threeb, i);
                    }
                    if(nu==4){
                        liview.setImgid(R.drawable.fourb, i);
                    }
                    if(nu==5){
                        liview.setImgid(R.drawable.fiveb, i);
                    }
                    if(nu==6){
                        liview.setImgid(R.drawable.sixb, i);
                    }
                    if(nu==7){
                        liview.setImgid(R.drawable.oneb, i);
                    }

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
                tag=rateNum[position][1];
                Intent intent = new Intent(ParentHome.this, ParentViewBProfile.class);
                intent.putExtra("p", position+1);
                int id = (getIntent().getExtras().getInt("id"));
                intent.putExtra("id",id);
                String numBB = (getIntent().getExtras().getString("n"));
                intent.putExtra("n",numBB);
                startActivity(intent);

            }
     });

    }

    public void Filter(View view) {
        Intent intent = new Intent(this, ParentSearch.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }
    public void Search(View view) {
        Intent intent = new Intent(this, ParentSearch.class);
        int id = (getIntent().getExtras().getInt("id"));
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void Profile(View view) {
        Intent intent = new Intent(this, ParentProfile.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }
    public void JobPost(View view) {
        Intent intent = new Intent(this, ParentPostJob.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }
    public void Home(View view) {
        Intent intent = new Intent(this, ParentHome.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        startActivity(intent);
    }

    public void Logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}

