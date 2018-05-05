/**
 * Created by jenny on 2018-04-06.
 */
package com.example.snd_v1;
public class Babysitter extends User{

    private int ratings;
    private String qualifications, experience, reviews;

    public Babysitter(String a,String e,String n,String p,String b,int g,String bi,String i, String q, String ex, String ag){
        super(a,e,n,p,b,g,bi,i,ag);
        qualifications=q;
        experience = ex;
        ratings=5;
        reviews="";
    }

    public String getQualifications (){
        return qualifications;
    }

    public String getExperience (){
        return experience;
    }

    public String getReviews (){
        return reviews;
    }
    public int getRatings (){
        return ratings;
    }

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
