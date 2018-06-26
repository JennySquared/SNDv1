package com.example.snd_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/*
   Title: Parent Post Job
   Author: Jenny Shen (modified by Jenny H - Error Checking)
   Date: March 20, 2018
   Description: Screen for Parents to enter information and creates a job posting for babysitters to enter
*/

public class ParentPostJob extends AppCompatActivity {

    Parent p = new Parent(); //user's parent object
    String d,s,e,i;// date, start time, end time, additional information

    EditText addInfo,tStart, tEnd,date;// text fields to enter date, start time, end time, additional information

    boolean timeFlag, dateFlag;
    Calendar now = Calendar.getInstance();
    public static int year, month, day;
    int jobDay, jobMonth, jobYear;

    //main method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_post_job);//set layout

        //link layout edit texts to the class' edit text
        date = findViewById(R.id.date);
        tStart = findViewById(R.id.tStart);
        tEnd = findViewById(R.id.address);
        addInfo = findViewById(R.id.addInfo);

        //get today's date
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        day = now.get(Calendar.DATE);
    }

    //when user presses submit button
    public void Submit(View view) {

        //intent to change screens
        final Intent intent = new Intent(this, ParentPostJob.class);
        final int id = (getIntent().getExtras().getInt("id"));//get user id from previous screen

        FirebaseDatabase database = FirebaseDatabase.getInstance();//intialize database
        DatabaseReference myRef = database.getReference("Users/Parent");//initialize reference


        if(dateFormatCheck(date.getText().toString())&&timeFormatCheck(tStart.getText().toString(), tEnd.getText().toString())) {
            getJobDate(date.getText().toString());
            if(checkIfDatePast(year, month, day, jobYear, jobMonth, jobDay)){
                d = date.getText().toString();

                Toast.makeText(this, d, Toast.LENGTH_LONG).show();
                s= tStart.getText().toString();
                e= tEnd.getText().toString();
                i= addInfo.getText().toString();

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        p=dataSnapshot.child(id+"").getValue(Parent.class);
                        p.setJob(d,s,e,i); //change job posting attributes within parent class with inputted values
                        setParent(p, id); //set modified parent into firebase
                        intent.putExtra("id",id);//add user id to next screen
                        startActivity(intent);//change screen
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        }
    }

    public boolean dateFormatCheck(String dateCheck){
        if (dateCheck.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
            return true;
        }
        else {
            date.setError("Invalid Format");
           return false;
        }
    }

    /*
    This method compares the information of the job date to the information of the current date in order
    to determine if the job date is valid or not. (Not in the past)
     */
    public boolean checkIfDatePast(int thisYear, int thisMonth, int thisDate, int jobYear, int jobMonth, int jobDate){
        if((thisYear==jobYear)||thisYear<jobYear){
          if(thisMonth==jobMonth||thisMonth<jobMonth){
              if(thisDate==jobDate||thisDate<jobDate){
                  return true;
              }

              else{
                  date.setError("Job posting must be in the future");
                  return false;
              }
          }
          else{
              date.setError("Job posting must be in the future");
              return false;
          }
        }
        date.setError("Job posting must be in the future");
        return false;
    }

    public void getJobDate(String format){ //Accessor method for job posting date
        String jobDayString = format.substring(3,5);
        String jobMonthString = format.substring(0,2);
        String jobYearString = format.substring(6);

        jobDay = Integer.parseInt(jobDayString);
        jobMonth = Integer.parseInt(jobMonthString);
        jobYear = Integer.parseInt(jobYearString);
    }

    //This method checks that the time is entered in the correct format
    public boolean timeFormatCheck(String start, String end) {
        if (!start.matches("([0-9]{2}):([0-9]{2})")) {
            tStart.setError("Invalid Format");
            timeFlag = false;
        }

        if (!end.matches("([0-9]{2}):([0-9]{2})")) {
            tEnd.setError("Invalid Format");
            timeFlag = false;
        }

        if (start.matches("([0-9]{2}):([0-9]{2})") && end.matches("([0-9]{2}):([0-9]{2})")) {
            timeFlag = true;
        }
        return timeFlag;
    }

    //set modified parent object with the new job posting into firebase
    public void setParent (Parent p, int id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/Parent");
        myRef.child(id+"").setValue(p);
    }

    //when user clicks the search icon in tool bar, screen changes to search screen
    public void Search(View view) {
        Intent intent = new Intent(this, ParentSearch.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
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
}
