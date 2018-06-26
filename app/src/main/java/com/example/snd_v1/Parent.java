package com.example.snd_v1;

/*
Title: Parent
Author: Jenny Shen
Date: April 6, 2018
Description: Parent child object of the User superclass, has additional attributes
such as child and job post
 */

public class Parent extends User {

    //additional attribute
    private String child;
    private JobPost job;

    //fully parameterized constructor
    public Parent(String a, String e, String n, String p, String b, int g, String c, String bi, String ag){
        super(a,e,n,p,b,g,bi,ag);
        child=c;
        job = new JobPost("0","0","0","0");
    }

    //constructor with no parameters (retrieving from Firebase)
    public Parent(){

    }

    //assessor methods
    public String getChild (){
        return child;
    }
    public JobPost getJob (){
        return job;
    }

    //mutator methods
    public void setChild(String a){
        child = a;
    }
    public void setJob(String d, String s, String e, String i){
        JobPost post = new JobPost(d,s,e,i);
        job=post;
    }

}
