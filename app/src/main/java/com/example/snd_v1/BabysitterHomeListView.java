package com.example.snd_v1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jenny on 2018-05-23.
 */

public class BabysitterHomeListView extends ArrayAdapter<String> {

    private String [] name;
    private String [] date;
    private Integer[] imgid;
    private String [] tEnd;
    private String [] tStart;

    private Activity context;

    public BabysitterHomeListView(@NonNull Activity context, String[] name, String[] date, Integer[] imgid, String[] tStart, String[] tEnd) {
        super(context, R.layout.babysitter_profile_list, name);

        this.context = context;
        this.name = name;
        this.tEnd = tEnd;
        this.tStart = tStart;
        this.date = date;
        this.imgid = imgid;
    }

    public void setName(String n, int index){
        name[index]=n;
    }
    public void setDate(String n, int index){


    }
    public void setTStart(String n, int index){

        tStart[index]="Time: "+n+"-  ";
    }
    public void setTEnd(String n, int index){
        tEnd[index]= "  "+n ;

    }
    public void setImgid(int n, int index){
        imgid[index]=n;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r = convertView;
        BabysitterHomeListView.ViewHolder viewHolder = null;
        if(r==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.babysitter_profile_list,null,true);
            viewHolder = new BabysitterHomeListView.ViewHolder(r);
            r.setTag(viewHolder);

        }
        else{
            viewHolder = (BabysitterHomeListView.ViewHolder) r.getTag();
        }
        viewHolder.ivw.setImageResource(imgid[position]);
        viewHolder.tvw1.setText(name[position]);
        viewHolder.tvw2.setText(date[position]);
        viewHolder.tvw3.setText(tEnd[position]);
        viewHolder.tvw4.setText(tStart[position]);
        return r;
    }

    class ViewHolder{

        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        TextView tvw4;
        ImageView ivw;
        ViewHolder (View v){
            tvw1= (TextView) v.findViewById(R.id.name);
            tvw2= (TextView) v.findViewById(R.id.date);
            tvw3= (TextView) v.findViewById(R.id.tStart);
            tvw4= (TextView) v.findViewById(R.id.address);
            ivw= (ImageView) v.findViewById(R.id.imgid);
        }
    }
}

