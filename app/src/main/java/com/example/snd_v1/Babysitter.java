
package com.example.snd_v1;

/*
Title: Babysitter
Author: Jenny Shen
Date: April 6, 2018
Description: Babysitter child object of the User superclass, has additional attributes
such as ratings, qualifications, experience and reviews
 */


public class Babysitter extends User{

    //additional attributes

    private String qualifications, experience, reviews, ratings;

    //parametrized constructor with all attributes
    public Babysitter(String a, String e, String n, String p, String b, int g, String bi, String q, String ex, String ag){
        super(a,e,n,p,b,g,bi,ag); //set variables in the inherited user class
        qualifications=q;
        experience = ex;
        ratings=((Math.random()*5)+"").substring(0,1);
        ratings = ""+1;
        reviews="";
    }

    //non parametrized constructor for Firebase retrieval
    public Babysitter(){

    }

    //assessor methods
    public String getQualifications (){
        return qualifications;
    }
    public String getExperience (){
        return experience;
    }
    public String getReviews (){
        return reviews;
    }
    public String getRatings (){
        return ratings;
    }

    //mutator methods
    public void setQualifications(String a){
        qualifications = a;
    }
    public void setExperience(String a){
        experience = a;
    }
    public void setReviews(String a){
        reviews = a;
    }
}
