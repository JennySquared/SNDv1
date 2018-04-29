package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   // FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("Users");
    public String[] num = new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        int counter=1;
//        for(int i =0;i<5;i++) {
//            Babysitter b = new Babysitter("51 Stick Drive", "jiskjb@gmail.com", "Jeremy Xu", "i like chesse", "December 5", 1, "my name is jen lala","myimage.jpg","First Aid, Cerificate", "LOL i have no experience");
//            myRef.child("Babysitter").child(counter + "").setValue(b);
//            counter++;
//        }
//        counter=1;
//        for(int i =0;i<5;i++) {
//            Parent b = new Parent("h9 Stick Drive", "jikhvhjb@gmail.com", "Jeremy lu", "i like chessejjj", "December 5", 1, "Female 10-12","ello", "myimage.jpg");
//            myRef.child("Parent").child(counter + "").setValue(b);
//            counter++;
//        }


    }

    public void sendMessage(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("numBabysitters");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                num[0] = dataSnapshot.getValue(String.class);
//                num=Integer.parseInt(numBB);
//                Toast.makeText(getApplicationContext(),"Read",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Intent intent = new Intent(this, ParentHome.class);
        intent.putExtra("n",num[0]);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterName.class);
        startActivity(intent);
    }


}
