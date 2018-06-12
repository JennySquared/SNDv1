package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference("Users");
    public String[] num = new String[3];
    public EditText email, password;
    boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
//        ImageView i = findViewById(R.id.imageView);
//        i.setImageResource(R.drawable.one);
        FirebaseApp.initializeApp(getApplicationContext());



        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference Ref = database.getReference();

//        int counter=1;
//        for(int i =0;i<5;i++) {
//            Babysitter b = new Babysitter();
//            Ref.child("Users").child("Babysitter").child(counter + "").setValue(b);
//            counter++;
//        }
//        counter=1;
//        for(int i =0;i<5;i++) {
//            Parent b = new Parent();
//            Ref.child("Users").child("Parent").child(counter + "").setValue(b);
//            counter++;
//        }

//        Parent b = new Parent("K8U2E4", "hitty@gmail.com", "Paul g" +
//                "", "jkkk", "e", 1, "f","h", "h","i");
//            Ref.child("Parent").child("4").setValue(b);

        DatabaseReference myRef = database.getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                num[0] = dataSnapshot.child("numBabysitters").getValue(String.class);
                /*num[1] = dataSnapshot.child("numParents").getValue(String.class);
                for(int i = 0; i<Integer.parseInt(num[1]); i++){
                    int numJobs =dataSnapshot.child("Users").child("Parent").child(i+1+"").child("jobs").getValue(ArrayList.class).size();
                    ArrayList<jobPost> jobs = dataSnapshot.child("Users").child("Parent").child(i+1+"").child("jobs").getValue(ArrayList.class);
                    if(numJobs>0){
                        for(int j = 0; j<numJobs;j++){
                            jobs.get(j).getDate();
                            jobs.get(j).getStart();
                            jobs.get(j).getEnd();
                            dataSnapshot.child("Users").child("Parent").child(i+1+"").child("Name");
                        }
                    }
                }*/
//                num=Integer.parseInt(numBB);
//                Toast.makeText(getApplicationContext(),"Read",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void log(View view) {
        final View v = view;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");
        final String username =email.getText().toString();
        final String pass = password.getText().toString();

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    for(int i = 0; i<(int)dataSnapshot.child("Parent").getChildrenCount();i++){
                        if(username.equals(dataSnapshot.child("Parent").child(i+1+"").child("email").getValue(String.class))){
                            if(pass.equals(dataSnapshot.child("Parent").child(i+1+"").child("password").getValue(String.class))){
                              //  Toast.makeText(getApplicationContext(),"Parent",Toast.LENGTH_SHORT).show();
                                pLogin(v, i+1);
                            }
                        }
                    }
                    for(int i = 0; i<(int)dataSnapshot.child("Babysitter").getChildrenCount();i++){
                    if(username.equals(dataSnapshot.child("Babysitter").child(i+1+"").child("email").getValue(String.class))){
                        if(pass.equals(dataSnapshot.child("Babysitter").child(i+1+"").child("password").getValue(String.class))){
                          //  Toast.makeText(getApplicationContext(),"babysitter",Toast.LENGTH_SHORT).show();
                            bbLogin(v, i+1);
                        }
                    }
                }
             //   Toast.makeText(getApplicationContext(),"Username or Password Invalid",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
                                //pLogin(view, 1);
    }


    public void register(View view) {
        Intent intent = new Intent(this, RegisterName.class);
        startActivity(intent);
    }

    public void bbLogin(View view, int id) {
        Intent intent = new Intent(this, BabysitterHome.class);
        intent.putExtra("n",num[0]);
        intent.putExtra("id", id);
        success=true;
        startActivity(intent);
    }
    public void pLogin(View view, int id) {
        Intent intent = new Intent(this, ParentHome.class);
        intent.putExtra("n",num[0]);
        intent.putExtra("id", id);
        success = true;
        startActivity(intent);
    }

}



