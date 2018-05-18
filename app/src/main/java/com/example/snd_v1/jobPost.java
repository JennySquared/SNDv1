package com.example.snd_v1;

/**
 * Created by jenny on 2018-04-06.
 */

public class jobPost {

    private String date, tStart, tEnd, addInfo;

    public jobPost(String d, String start,  String end, String info){
        setDate(d);
        setEnd(end);
        setStart(start);
        setInfo(info);
    }

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
