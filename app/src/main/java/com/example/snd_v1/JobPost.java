package com.example.snd_v1;

/*
Title: Job Post
Author: Jenny S
Date: April 6, 2018
Description: Job Post for Parent including the date, start and end time, and additional
information about the job
 */

public class JobPost {

    //attributes for the job post section
    private String date, tStart, tEnd, addInfo;

    //all parameterized constructor (used when parent submits a jobpost)
    public JobPost(String d, String start, String end, String info){
        setDate(d);
        setEnd(end);
        setStart(start);
        setInfo(info);
    }

    //non parameterized constructor (used when retrieving a job post from firebase)
    public JobPost(){

    }

    //assessor methods
    public String getStart(){
        return tStart;
    }

    public String getEnd(){
        return tEnd;
    }

    public String getDate(){
        return date;
    }

    public String getInfo(){
        return addInfo;
    }

    //mutator methods
    public void setDate(String d){
        date=d;
    }

    public void setEnd(String e){
        tEnd = e;
    }

    public void setStart(String s){
        tStart=s;
    }

    public void setInfo(String i){
        addInfo=i;
    }

}
