package com.example.snd_v1;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by jenny on 2018-04-06.
 */

public class Parent extends User {
    private String child;
    private jobPost[] jobs;
    private jobPost job;
    private int numJobs;

    public Parent(String a, String e, String n, String p, String b, int g, String c, String bi, String ag){
        super(a,e,n,p,b,g,bi,ag);
        child=c;
        jobs= new jobPost[15];
        numJobs=0;
        job = new jobPost("0","0","0","0");

    }

    public Parent(){

    }

    public String getChild (){
        return child;
    }

    public jobPost getJob (){
        return job;
    }

    public void setChild(String a){
        child = a;
    }

    public void setJob(String d, String s, String e, String i){
        jobPost post = new jobPost(d,s,e,i);
        job=post;
    }
    public void createJob (String d, String s, String e, String i){
        jobPost post = new jobPost(d,s,e,i);
        jobs[numJobs]=post;
        numJobs++;

        if(numJobs==19){
            numJobs=0;
        }
    }


}
