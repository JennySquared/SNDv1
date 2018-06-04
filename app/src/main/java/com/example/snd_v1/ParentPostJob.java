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

public class ParentPostJob extends AppCompatActivity {
    Parent p = new Parent();
    String d,s,e,i;
    EditText addInfo,tStart, tEnd,date;
    boolean timeFlag, dateFlag;
    Calendar now = Calendar.getInstance();
    public static int year, month, day;
    int jobDay, jobMonth, jobYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_post_job);

        date = findViewById(R.id.date);
        tStart = findViewById(R.id.tStart);
        tEnd = findViewById(R.id.address);
        addInfo = findViewById(R.id.addInfo);

        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        day = now.get(Calendar.DATE);
    }

    public void Submit(View view) {
        final Intent intent = new Intent(this, ParentPostJob.class);
        final int id = (getIntent().getExtras().getInt("id"));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/Parent");

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
                        p.setJob(d,s,e,i);
                        setParent(p, id);
                        intent.putExtra("id",id);
                        startActivity(intent);
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

    public void getJobDate(String format){
        String jobDayString = format.substring(3,5);
        String jobMonthString = format.substring(0,2);
        String jobYearString = format.substring(6);

        jobDay = Integer.parseInt(jobDayString);
        jobMonth = Integer.parseInt(jobMonthString);
        jobYear = Integer.parseInt(jobYearString);
    }

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

    public void setParent (Parent p, int id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/Parent");
        myRef.child(id+"").setValue(p);
    }

    public void Search(View view) {
        Intent intent = new Intent(this, ParentSearch.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
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
}
