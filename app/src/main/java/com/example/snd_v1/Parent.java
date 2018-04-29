package com.example.snd_v1;

import java.util.ArrayList;

/**
 * Created by jenny on 2018-04-06.
 */

public class Parent extends User {
    private String child, bio;
    public Parent(String a,String e,String n,String p,String b,int g, String c, String bi, String i){
        super(a,e,n,p,b,g,bi,i);
        child=c;

    }

    public String getChild (){
        return child;
    }


    public void setChild(String a){
        child = a;
    }


}
